package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.service.HomeService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(
            @RequestParam Integer uid,
            @RequestParam String code
    ) throws ResourceNotFoundException {
        Boolean verifyAccount = homeService.verifyAccount(uid, code);
        if (!verifyAccount) {
            return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuilderResponseMessage("Account verified successfully", HttpStatus.OK);

    }

}
