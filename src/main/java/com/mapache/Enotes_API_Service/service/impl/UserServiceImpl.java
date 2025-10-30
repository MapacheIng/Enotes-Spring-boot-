package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.dto.EmailRequest;
import com.mapache.Enotes_API_Service.dto.UserDto;
import com.mapache.Enotes_API_Service.entity.Role;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.repository.RoleRepository;
import com.mapache.Enotes_API_Service.repository.UserRepository;
import com.mapache.Enotes_API_Service.service.UserService;
import com.mapache.Enotes_API_Service.util.Validation;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Validation validation;
    private final ModelMapper mapper;
    private final EmailService emailService;


    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           Validation validation,
                           ModelMapper mapper,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validation = validation;
        this.mapper = mapper;
        this.emailService = emailService;
    }

    @Override
    public Boolean register(UserDto userDto) throws MessagingException, UnsupportedEncodingException {
        validation.userValidation(userDto);
        User user = mapper.map(userDto, User.class);
        setRole(userDto, user);
        User save = userRepository.save(user);
        if(ObjectUtils.isEmpty(save)){
            return false;
        }
        emailSend(save);

        return !ObjectUtils.isEmpty(save);

    }

    private void emailSend(User save) throws MessagingException, UnsupportedEncodingException {

        String message="Hi, <b> "+save.getFirstName()+" </b> "
                + "<br> Your account register sucessfully.<br>"
                +"<br> Click the below link verify & Active your account <br>"
                +"<a href='#'>Click Here</a> <br><br>"
                +"Thanks,<br>Enotes.com"
                ;

        EmailRequest emailRequest = EmailRequest.builder()
                .to(save.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Successfully")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);
    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> reqRoleId = userDto.getRoles().stream()
                .map(UserDto.RoleDto::getId)
                .toList();
        List<Role> roles = roleRepository.findAllById(reqRoleId);
        user.setRoles(roles);
    }
}
