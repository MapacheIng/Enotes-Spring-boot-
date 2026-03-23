package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.dto.PasswordChangeRequest;
import com.mapache.Enotes_API_Service.dto.PasswordResetRequest;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface UserService {

    void changePassword(PasswordChangeRequest passwordChangeRequest);

    void sendEmailPasswordReset(String email, HttpServletRequest request) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException;

    void verifyPasswordResetLink(Integer uid, String code) throws ResourceNotFoundException;

    void resetPassword(PasswordResetRequest passwordResetRequest) throws ResourceNotFoundException;
}
