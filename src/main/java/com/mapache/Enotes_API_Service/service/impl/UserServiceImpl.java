package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.dto.EmailRequest;
import com.mapache.Enotes_API_Service.dto.PasswordChangeRequest;
import com.mapache.Enotes_API_Service.dto.PasswordResetRequest;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.repository.UserRepository;
import com.mapache.Enotes_API_Service.service.UserService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;




    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }


    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        User loggedInUser = CommonUtil.getLoggedInUser();
        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        loggedInUser.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(loggedInUser);

    }

    @Override
    public void sendEmailPasswordReset(String email, HttpServletRequest request) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Invalid email address provided"));
        String url = CommonUtil.getUrl(request);

        String passwordResetToken = UUID.randomUUID().toString();
        user.getStatus().setPasswordResetToken(passwordResetToken);
        User updateUser = userRepository.save(user);
        sendEmailRequest(updateUser, url);


    }

    @Override
    public void verifyPasswordResetLink(Integer uid, String code) throws ResourceNotFoundException {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid user id provided"));
        verifyPasswordResetCode(user.getStatus().getPasswordResetToken(), code);
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) throws ResourceNotFoundException {
        User user = userRepository.findById(passwordResetRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid user id provided"));
        String encodePassword = passwordEncoder.encode(passwordResetRequest.getNewPassword());
        user.setPassword(encodePassword);
        user.getStatus().setPasswordResetToken(null);
        userRepository.save(user);


    }

    private void verifyPasswordResetCode(String existToken, String reqToken) {
        if (!StringUtils.hasText(reqToken)){
            throw new IllegalArgumentException("Invalid token");
        }
        if (!StringUtils.hasText(existToken)){
            throw new IllegalArgumentException("Already Password Reset ");
        }
        if (!existToken.equals(reqToken)){
            throw new IllegalArgumentException("Invalid link");
        }


    }

    private void sendEmailRequest(User user, String url) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        String message = """
        Hi, <b>%s</b><br>
        <p>You have request to reset your password.</p>
        <p>Click the below link to change your password:</p>
        <a href='%s'>Change my password</a><br><br>
        <p>Ignore this email if you do remember your password, or you have not made the request.</p><br>
        Thanks,<br>
        Enotes.com
        """.formatted(
                user.getFirstName(),
                url + "/api/v1/home/verify-pswd-link?uid=" + user.getId() + "&code=" + user.getStatus().getPasswordResetToken()
        );


        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .title("Password Reset")
                .subject("password Reset Link")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);
    }




}
