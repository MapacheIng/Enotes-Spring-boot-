package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.UserRequest;
import com.mapache.Enotes_API_Service.dto.UserResponse;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final ModelMapper mapper;

    public UserController(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        User loggedInUser = CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
        return CommonUtil.createBuilderResponse(userResponse, HttpStatus.OK);
    }

}
