package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;

public interface HomeService {

    public Boolean verifyAccount(Integer userId, String verificationCode) throws ResourceNotFoundException;

}
