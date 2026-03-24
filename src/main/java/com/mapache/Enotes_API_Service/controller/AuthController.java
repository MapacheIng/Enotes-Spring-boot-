package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.LoginRequest;
import com.mapache.Enotes_API_Service.dto.LoginResponse;
import com.mapache.Enotes_API_Service.dto.UserRequest;
import com.mapache.Enotes_API_Service.endpoint.AuthEndpoint;
import com.mapache.Enotes_API_Service.service.AuthService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
class AuthController implements AuthEndpoint {


    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        log.info("AuthController : registerUser() : Execution Started");
        String url = CommonUtil.getUrl(request);
        Boolean register = authService.register(userRequest, url);
        if (!register) {
            return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("AuthController : registerUser() : Execution Ended");
        return CommonUtil.createBuilderResponseMessage("User registered successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        if (ObjectUtils.isEmpty(loginResponse)) {
            return CommonUtil.createErrorResponseMessage("Login failed", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuilderResponse(loginResponse, HttpStatus.OK);
    }
}
