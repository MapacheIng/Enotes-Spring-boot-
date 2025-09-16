package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.NotesDto;
import com.mapache.Enotes_API_Service.entity.FileDetails;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.service.NotesService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class NotesController {

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


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) throws ResourceNotFoundException, IOException {
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] data = notesService.downloadFile(fileDetails);
        HttpHeaders headers = new HttpHeaders();
        String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
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
