package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.PasswordResetRequest;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(
            @RequestParam Integer uid,
            @RequestParam String code
    ) throws ResourceNotFoundException;

    @GetMapping("/send-email-reset")
    ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request)
            throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException;

    @GetMapping("/verify-pswd-link")
    ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code)
            throws ResourceNotFoundException;

    @PostMapping("/reset-pswd")
    ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest)
            throws ResourceNotFoundException;

}
