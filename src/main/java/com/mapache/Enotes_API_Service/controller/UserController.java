package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.PasswordChangeRequest;
import com.mapache.Enotes_API_Service.dto.UserRequest;
import com.mapache.Enotes_API_Service.dto.UserResponse;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.service.UserService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final ModelMapper mapper;
    private final UserService userService;


    public UserController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        User loggedInUser = CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
        return CommonUtil.createBuilderResponse(userResponse, HttpStatus.OK);
    }

    @PostMapping("/chng-pswd")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordRequest) {
        userService.changePassword(passwordRequest);
        return CommonUtil.createBuilderResponseMessage("Password Change Success", HttpStatus.OK);
    }

}
