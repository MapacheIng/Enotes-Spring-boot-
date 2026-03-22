package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.dto.PasswordChangeRequest;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.repository.UserRepository;
import com.mapache.Enotes_API_Service.service.UserService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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

}
