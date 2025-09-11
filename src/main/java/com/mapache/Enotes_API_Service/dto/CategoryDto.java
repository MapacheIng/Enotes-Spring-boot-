package com.mapache.Enotes_API_Service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotBlank
    @Min(value = 5)
    @Max(value = 100)
    private String name;
    @NotBlank
    @Min(value = 5)
    @Max(value = 100)
    private String description;
    @NotNull
    private Boolean isActive;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;
}
