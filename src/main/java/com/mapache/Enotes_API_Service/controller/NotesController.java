package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.NotesDto;
import com.mapache.Enotes_API_Service.dto.NotesResponse;
import com.mapache.Enotes_API_Service.entity.FileDetails;
import com.mapache.Enotes_API_Service.entity.Notes;
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


    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveNotes(
            @RequestParam String notes,
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
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> notes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(notes, HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer userId = 2;
        NotesResponse notes = notesService.getAllNotesByUser(userId, pageNo, pageSize);
        return CommonUtil.createBuilderResponse(notes, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws ResourceNotFoundException {
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuilderResponseMessage("Note deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws ResourceNotFoundException {
        notesService.restoreNotes(id);
        return CommonUtil.createBuilderResponseMessage("Notes restore successfully", HttpStatus.OK);
    }

    @GetMapping("/recycle-bin")
    public ResponseEntity<?> getUserRecycleBinNotes() throws ResourceNotFoundException {
        Integer userId = 2;
        List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);
        if (CollectionUtils.isEmpty(notes)) {
            return CommonUtil.createErrorResponseMessage("Notes not available in Recycle bien", HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuilderResponse(notes, HttpStatus.OK);
    }



}
