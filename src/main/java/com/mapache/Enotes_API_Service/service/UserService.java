package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.dto.PasswordChangeRequest;

public interface UserService {

    void changePassword(PasswordChangeRequest passwordChangeRequest);

}
