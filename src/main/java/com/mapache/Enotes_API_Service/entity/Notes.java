package com.mapache.Enotes_API_Service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notes extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private Boolean isDeleted;

    private LocalDateTime deletedOn;

    @ManyToOne
    //@JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    private FileDetails fileDetails;

}
