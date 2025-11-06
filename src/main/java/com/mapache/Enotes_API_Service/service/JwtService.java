package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.entity.User;

public interface JwtService {

    String generateToken(User user);

}
