package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.PasswordResetRequest;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Tag(name = "Home", description = "All the Home APIs")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

    @Operation(summary = "Verify User Account Endpoint", description = "Verifies a user's account using a verification code sent to their email.")
    @GetMapping("/verify")
    ResponseEntity<?> verifyUserAccount(
            @RequestParam Integer uid,
            @RequestParam String code
    ) throws ResourceNotFoundException;

    @Operation(summary = "Send Password Reset Email Endpoint", description = "Sends an email to the user with a link to reset their password.")
    @GetMapping("/send-email-reset")
    ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request)
            throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException;

    @Operation(summary = "Verify Password Reset Link Endpoint", description = "Verifies the password reset link using the user ID and verification code.")
    @GetMapping("/verify-pswd-link")
    ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code)
            throws ResourceNotFoundException;

    @Operation(summary = "Reset Password Endpoint", description = "Resets the user's password using the provided new password and verification code.")
    @PostMapping("/reset-pswd")
    ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest)
            throws ResourceNotFoundException;

}
