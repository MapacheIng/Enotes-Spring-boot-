package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.LoginRequest;
import com.mapache.Enotes_API_Service.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@Tag(name = "Authentication", description = "All the User Authentication APIs")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

    @Operation(summary = "Register a new user", description = "Registers a new user and sends a verification email.")
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException;

    @Operation(summary = "User Login Endpoint", description = "Authenticates a user and returns a JWT token.")
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);

}
