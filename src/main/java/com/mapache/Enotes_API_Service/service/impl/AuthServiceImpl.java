package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.config.security.CustomUserDetails;
import com.mapache.Enotes_API_Service.dto.*;
import com.mapache.Enotes_API_Service.entity.AccountStatus;
import com.mapache.Enotes_API_Service.entity.Role;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.repository.RoleRepository;
import com.mapache.Enotes_API_Service.repository.UserRepository;
import com.mapache.Enotes_API_Service.service.JwtService;
import com.mapache.Enotes_API_Service.service.AuthService;
import com.mapache.Enotes_API_Service.util.Validation;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Validation validation;
    private final ModelMapper mapper;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           Validation validation,
                           ModelMapper mapper,
                           EmailService emailService,
                           AuthenticationManager authenticationManager,
                           BCryptPasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validation = validation;
        this.mapper = mapper;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public Boolean register(UserRequest userRequest, String url) throws MessagingException, UnsupportedEncodingException {
        validation.userValidation(userRequest);
        User user = mapper.map(userRequest, User.class);
        setRole(userRequest, user);

        AccountStatus accountStatus = AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(accountStatus);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User save = userRepository.save(user);
        if(ObjectUtils.isEmpty(save)){
            return false;
        }
        emailSend(save, url);

        return !ObjectUtils.isEmpty(save);

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {


        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        if (authenticate.isAuthenticated()){
            CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();

            String token = jwtService.generateToken(customUserDetails.getUser());

            return LoginResponse.builder()
                    .user(mapper.map(customUserDetails.getUser(), UserResponse.class))
                    .token(token)
                    .build();
        }

        return null;
    }

    private void emailSend(User save, String url) throws MessagingException, UnsupportedEncodingException {

        String message = """
        Hi, <b>%s</b><br>
        Your account registered successfully.<br>
        <br>
        Click the below link to verify & activate your account:<br>
        <a href='%s'>Click Here</a><br><br>
        Thanks,<br>
        Enotes.com
        """.formatted(
                save.getFirstName(),
                url + "/api/v1/home/verify?uid=" + save.getId() + "&code=" + save.getStatus().getVerificationCode()
        );


        EmailRequest emailRequest = EmailRequest.builder()
                .to(save.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Successfully")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);
    }

    private void setRole(UserRequest userRequest, User user) {
        List<Integer> reqRoleId = userRequest.getRoles().stream()
                .map(UserRequest.RoleDto::getId)
                .toList();
        List<Role> roles = roleRepository.findAllById(reqRoleId);
        user.setRoles(roles);
    }
}
