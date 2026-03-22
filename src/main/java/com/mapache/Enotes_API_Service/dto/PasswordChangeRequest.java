package com.mapache.Enotes_API_Service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
}
