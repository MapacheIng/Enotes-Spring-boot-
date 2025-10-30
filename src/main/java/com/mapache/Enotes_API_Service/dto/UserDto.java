package com.mapache.Enotes_API_Service.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDto {
    Integer id;
    String firstName;
    String lastName;
    String email;
    String mobNumber;
    String password;
    List<RoleDto> roles;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class RoleDto {
        Integer id;
        String name;
    }


}