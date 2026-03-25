package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.FavouriteNoteDto;
import com.mapache.Enotes_API_Service.dto.NotesDto;
import com.mapache.Enotes_API_Service.dto.NotesResponse;
import com.mapache.Enotes_API_Service.endpoint.NotesEndpoint;
import com.mapache.Enotes_API_Service.entity.FileDetails;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.service.NotesService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class NotesController implements NotesEndpoint {

    private NotesService notesService;


    @Override
    public ResponseEntity<Map<String, Object>> saveNotes(String notes, MultipartFile file)
            throws ResourceNotFoundException, IOException {
        boolean noteSave = notesService.saveNotes(notes, file);
        if (!noteSave) {
            return CommonUtil.createBuilderResponseMessage("Note not saved successfully", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuilderResponseMessage("Note saved successfully", HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<byte[]> downloadFile(Integer id) throws ResourceNotFoundException, IOException {
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


    @Override
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> notes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(notes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllNotesByUser(Integer pageNo, Integer pageSize) {
        NotesResponse notes = notesService.getAllNotesByUser(pageNo, pageSize);
        return CommonUtil.createBuilderResponse(notes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> searchNotes(String key, Integer pageNo, Integer pageSize) {
        NotesResponse notes = notesService.getNotesByUserSearch(pageNo, pageSize, key);
        return CommonUtil.createBuilderResponse(notes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteNotes(Integer id) throws ResourceNotFoundException {
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuilderResponseMessage("Note deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> restoreNotes(Integer id) throws ResourceNotFoundException {
        notesService.restoreNotes(id);
        return CommonUtil.createBuilderResponseMessage("Notes restore successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserRecycleBinNotes()  {
        List<NotesDto> notes = notesService.getUserRecycleBinNotes();
        if (CollectionUtils.isEmpty(notes)) {
            return CommonUtil.createBuilderResponseMessage("Recycle bin is empty", HttpStatus.OK);
        }
        return CommonUtil.createBuilderResponse(notes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> hardDeleteNotes(Integer id) throws ResourceNotFoundException {
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuilderResponseMessage("Note deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> emptyUserRecycleBin()  {
        notesService.emptyRecycleBin();
        return CommonUtil.createBuilderResponseMessage("Note deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> favoriteNote(Integer noteId) throws ResourceNotFoundException {
        notesService.favoriteNotes(noteId);
        return CommonUtil.createBuilderResponseMessage("Notes added Favorite", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> unfavoriteNote(Integer favNoteId) throws ResourceNotFoundException {
        notesService.unFavoriteNotes(favNoteId);
        return CommonUtil.createBuilderResponseMessage("Remove Favorite", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserFavoriteNote() {
        List<FavouriteNoteDto> userFavoriteNotes = notesService.getUserFavoriteNotes();
        if (CollectionUtils.isEmpty(userFavoriteNotes)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(userFavoriteNotes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> copyNote(Integer id) throws ResourceNotFoundException {
        boolean copyNotes = notesService.copyNotes(id);
        if (!copyNotes) {
            return CommonUtil.createErrorResponseMessage("Copy failed!! Try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuilderResponseMessage("Copied success", HttpStatus.CREATED);
    }



}
