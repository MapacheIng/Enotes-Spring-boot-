package com.mapache.Enotes_API_Service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mapache.Enotes_API_Service.dto.NotesDto;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.service.NotesService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/notes")
public class NotesCategory {

    private NotesService notesService;

//    @PostMapping("/save")
//    public ResponseEntity<Map<String, Object>> saveNotes(@RequestBody NotesDto notesDto) throws ResourceNotFoundException {
//        boolean noteSave = notesService.saveNotes(notesDto);
//        if (!noteSave) {
//            return CommonUtil.createBuilderResponseMessage("Note not saved successfully", HttpStatus.BAD_REQUEST);
//        }
//        return CommonUtil.createBuilderResponseMessage("Note saved successfully", HttpStatus.CREATED);
//    }


    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveNotes(@RequestParam String notes,
                                                         @RequestParam(required = false) MultipartFile file)
            throws ResourceNotFoundException, IOException {
        boolean noteSave = notesService.saveNotes(notes, file);
        if (!noteSave) {
            return CommonUtil.createBuilderResponseMessage("Note not saved successfully", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuilderResponseMessage("Note saved successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllNotes(NotesDto notesDto) {
        List<NotesDto> notes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(notes, HttpStatus.OK);
    }



}
