package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User", description = "User operation APIs")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @Operation(summary = "Get user profile", description = "Get the profile information of the authenticated user")
    @GetMapping("/profile")
    ResponseEntity<?> getProfile();

    @Operation(summary = "Change user password", description = "Change the password of the authenticated user")
    @PostMapping("/chng-pswd")
    ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordRequest);

}
