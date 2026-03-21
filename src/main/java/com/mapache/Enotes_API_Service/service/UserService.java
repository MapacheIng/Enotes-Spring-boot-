package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.dto.LoginRequest;
import com.mapache.Enotes_API_Service.dto.LoginResponse;
import com.mapache.Enotes_API_Service.dto.UserRequest;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface UserService {

    Boolean register(UserRequest userRequest, String url) throws MessagingException, UnsupportedEncodingException;


    LoginResponse login(LoginRequest loginRequest);
}
