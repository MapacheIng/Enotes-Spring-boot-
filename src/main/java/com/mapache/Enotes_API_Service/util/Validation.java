package com.mapache.Enotes_API_Service.util;

import com.mapache.Enotes_API_Service.dto.CategoryDto;
import com.mapache.Enotes_API_Service.dto.TodoDto;
import com.mapache.Enotes_API_Service.dto.UserDto;
import com.mapache.Enotes_API_Service.entity.Role;
import com.mapache.Enotes_API_Service.enums.TodoStatus;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.exception.ValidationException;
import com.mapache.Enotes_API_Service.repository.RoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validation {


    private final RoleRepository roleRepository;

    public Validation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void categoryValidation(CategoryDto categoryDto) {



        Map<String, Object> error = new LinkedHashMap<>();

        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("category Object/JSON shouldn't be null or empty");
        } else {

            // validation name field
            if (ObjectUtils.isEmpty(categoryDto.getName())) {
                error.put("name", "name field is empty or null");
            } else {
                if (categoryDto.getName().length() < 3) {
                    error.put("name", "name length min 3");
                }
                if (categoryDto.getName().length() > 100) {
                    error.put("name", "name length max 100");
                }
            }

            // validation dscription
            if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
                error.put("description", "description field is empty or null");
            }

            // validation isActive
            if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
                error.put("isActive", "isActive field is empty or null");
            } else {
                if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue()
                        && categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
                    error.put("isActive", "invalid value isActive field ");
                }
            }
        }

        if (!error.isEmpty()) {
            throw new ValidationException(error);
        }

    }

    public void todoValidation(TodoDto todoDto) throws ResourceNotFoundException {

        boolean exists = Arrays.stream(TodoStatus.values())
                .anyMatch(ts -> ts.getId().equals(todoDto.getStatus().getId()));
        if (!exists) {
            throw new ResourceNotFoundException("Invalid status");
        }
    }


    public void userValidation(UserDto userDto) {

        if(!StringUtils.hasText(userDto.getFirstName())){
            throw new IllegalArgumentException("first name field is invalid");
        }
        if (!StringUtils.hasText(userDto.getLastName())){
            throw new IllegalArgumentException("last name field is invalid");
        }
        if (!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)){
            throw new IllegalArgumentException("email field is invalid");
        }

        if (!StringUtils.hasText(userDto.getMobNumber()) || !userDto.getMobNumber().matches(Constants.MOB_NUMBER_REGEX)){
            throw new IllegalArgumentException("mobile number field is invalid");
        }

        if (CollectionUtils.isEmpty(userDto.getRoles())) {
            throw  new IllegalArgumentException("roles field is invalid");
        }

        List<Integer> rolesIds = roleRepository.findAll().stream()
                .map(Role::getId)
                .toList();

        List<Integer> invalidReqRoleIds = userDto.getRoles().stream()
                .map(UserDto.RoleDto::getId)
                .filter(r -> !rolesIds.contains(r))
                .toList();

        if (!CollectionUtils.isEmpty(invalidReqRoleIds)) {
            throw   new IllegalArgumentException("roles is invalid " + invalidReqRoleIds);
        }


    }



}
