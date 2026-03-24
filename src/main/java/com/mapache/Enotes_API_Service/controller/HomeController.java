package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.PasswordResetRequest;
import com.mapache.Enotes_API_Service.endpoint.HomeEndpoint;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.service.HomeService;
import com.mapache.Enotes_API_Service.service.UserService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
public class HomeController implements HomeEndpoint {

    private final HomeService homeService;
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(HomeController.class);


    public HomeController(HomeService homeService, UserService userService) {
        this.homeService = homeService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> verifyUserAccount(
            @RequestParam Integer uid,
            @RequestParam String code
    ) throws ResourceNotFoundException {
        logger.info("HomeController : verifyUserAccount() : Execution Started");
        Boolean verifyAccount = homeService.verifyAccount(uid, code);
        if (!verifyAccount) {
            return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);
        }
        logger.info("HomeController : verifyUserAccount() : Execution Ended");
        return CommonUtil.createBuilderResponseMessage("Account verified successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        userService.sendEmailPasswordReset(email, request);
        return CommonUtil.createBuilderResponseMessage("Password reset email sent successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws ResourceNotFoundException {
        userService.verifyPasswordResetLink(uid, code);
        return CommonUtil.createBuilderResponseMessage("Verification success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws ResourceNotFoundException {
        userService.resetPassword(passwordResetRequest);
        return CommonUtil.createBuilderResponseMessage("Password reset successfully", HttpStatus.OK);
    }

}
