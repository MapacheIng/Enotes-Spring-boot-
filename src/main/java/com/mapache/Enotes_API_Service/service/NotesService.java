package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.dto.NotesDto;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException;

    public List<NotesDto> getAllNotes();

}
