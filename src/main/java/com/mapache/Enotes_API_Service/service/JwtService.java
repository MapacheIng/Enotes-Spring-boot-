package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    Boolean validateToken(String token, UserDetails userDetails);

}
