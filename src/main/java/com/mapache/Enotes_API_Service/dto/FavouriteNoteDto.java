package com.mapache.Enotes_API_Service.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteNoteDto {
    private Integer id;
    private NotesDto note;
    private Integer userId;
}