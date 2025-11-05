package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.dto.LoginRequest;
import com.mapache.Enotes_API_Service.dto.LoginResponse;
import com.mapache.Enotes_API_Service.dto.UserDto;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface UserService {

    Boolean register(UserDto userDto, String url) throws MessagingException, UnsupportedEncodingException;


    LoginResponse login(LoginRequest loginRequest);
}
