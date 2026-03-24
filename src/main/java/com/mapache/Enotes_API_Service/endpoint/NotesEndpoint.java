package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<Map<String, Object>> saveNotes(
            @RequestParam String notes,
            @RequestParam(required = false) MultipartFile file)
            throws ResourceNotFoundException, IOException;

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) throws ResourceNotFoundException, IOException;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getAllNotesByUser(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @GetMapping("/search-notes")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> searchNotes(
            @RequestParam(name = "key") String key,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws ResourceNotFoundException;

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws ResourceNotFoundException;

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUserRecycleBinNotes();

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws ResourceNotFoundException;

    @DeleteMapping("/delete-recycle-bin")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> emptyUserRecycleBin();

    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> favoriteNote(@PathVariable Integer noteId) throws ResourceNotFoundException;

    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> unfavoriteNote(@PathVariable Integer favNoteId) throws ResourceNotFoundException;

    @GetMapping("/fav-notes")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUserFavoriteNote();

    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> copyNote(@PathVariable Integer id) throws ResourceNotFoundException;

}
