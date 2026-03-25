package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.mapache.Enotes_API_Service.util.Constants.*;


import java.io.IOException;
import java.util.Map;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/save")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<Map<String, Object>> saveNotes(
            @RequestParam String notes,
            @RequestParam(required = false) MultipartFile file)
            throws ResourceNotFoundException, IOException;

    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) throws ResourceNotFoundException, IOException;

    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllNotesByUser(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @GetMapping("/search-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> searchNotes(
            @RequestParam(name = "key") String key,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws ResourceNotFoundException;

    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws ResourceNotFoundException;

    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserRecycleBinNotes();

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws ResourceNotFoundException;

    @DeleteMapping("/delete-recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> emptyUserRecycleBin();

    @GetMapping("/fav/{noteId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> favoriteNote(@PathVariable Integer noteId) throws ResourceNotFoundException;

    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> unfavoriteNote(@PathVariable Integer favNoteId) throws ResourceNotFoundException;

    @GetMapping("/fav-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserFavoriteNote();

    @GetMapping("/copy/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> copyNote(@PathVariable Integer id) throws ResourceNotFoundException;

}
