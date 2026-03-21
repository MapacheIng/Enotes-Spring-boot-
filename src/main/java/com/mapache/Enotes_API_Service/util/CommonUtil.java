package com.mapache.Enotes_API_Service.util;

import com.mapache.Enotes_API_Service.config.security.CustomUserDetails;
import com.mapache.Enotes_API_Service.dto.UserResponse;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.handler.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class CommonUtil {

    public static ResponseEntity<Map<String, Object>> createBuilderResponse(Object data, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message("success")
                .data(data)
                .build();
        return response.create();
    }


    public static ResponseEntity<Map<String, Object>> createBuilderResponseMessage(String message, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message(message)
                .build();
        return response.create();
    }

    public static ResponseEntity<Map<String, Object>> createErrorResponse(Object data, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message("failed")
                .data(data)
                .build();
        return response.create();
    }

    public static ResponseEntity<Map<String, Object>> createErrorResponseMessage(String message, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message(message)
                .build();
        return response.create();
    }

    public static String getContentType(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName); // java_programing.pdf

        return switch (extension) {
            case "pdf" -> "application/pdf";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
            case "txt" -> "text/plan";
            case "png" -> "image/png";
            case "jpeg" -> "image/jpeg";
            default -> "application/octet-stream";
        };
    }

    public static String getUrl(HttpServletRequest request) {
        String apiUrl = request.getRequestURL().toString(); // http://localhost:8081/api/v1/user
        apiUrl = apiUrl.replace(request.getServletPath(), ""); // http://localhost:8081
        return apiUrl;
    }

    public static User getLoggedInUser(){
        CustomUserDetails logUser = (CustomUserDetails) SecurityContextHolder.getContext()
                                                                                .getAuthentication().getPrincipal();
        return  logUser.getUser();

    }

}
