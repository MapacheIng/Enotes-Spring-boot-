package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.UserDto;
import com.mapache.Enotes_API_Service.service.UserService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/user")
class AuthController {


    private final UserService userService;

    AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String url = CommonUtil.getUrl(request);
        Boolean register = userService.register(userDto, url);
        if (!register) {
            return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuilderResponseMessage("User registered successfully", HttpStatus.CREATED);
    }
}
