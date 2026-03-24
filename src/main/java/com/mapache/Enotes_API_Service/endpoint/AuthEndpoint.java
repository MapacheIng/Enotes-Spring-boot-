package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.LoginRequest;
import com.mapache.Enotes_API_Service.dto.UserRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);

}
