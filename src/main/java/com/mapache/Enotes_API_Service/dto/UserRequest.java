package com.mapache.Enotes_API_Service.dto;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserRequest {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobNumber;
    private String password;
    private List<RoleDto> roles;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class RoleDto {
        private Integer id;
        private String name;
    }


}