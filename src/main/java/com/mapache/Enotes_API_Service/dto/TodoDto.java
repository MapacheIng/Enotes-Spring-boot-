package com.mapache.Enotes_API_Service.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TodoDto  {
    private Integer id;
    private String title;
    private StatusDto status;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class StatusDto {
        private Integer id;
        private String name;
    }
}