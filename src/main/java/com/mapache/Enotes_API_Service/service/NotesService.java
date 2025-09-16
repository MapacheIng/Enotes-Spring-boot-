package com.mapache.Enotes_API_Service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mapache.Enotes_API_Service.dto.NotesDto;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NotesService {

//    public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException;
    public Boolean saveNotes(String notes, MultipartFile file) throws ResourceNotFoundException, IOException;

    public List<NotesDto> getAllNotes();

}
