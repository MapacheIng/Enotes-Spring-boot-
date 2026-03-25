package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.NotesRequest;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.mapache.Enotes_API_Service.util.Constants.*;


import java.io.IOException;
import java.util.Map;

@Tag(name = "Notes", description = "All the Notes operation APIs")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @Operation(summary = "Save notes Endpoint", description = "Saves a new note to the database, with an optional file attachment.")
    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    @PreAuthorize(ROLE_USER)
    ResponseEntity<Map<String, Object>> saveNotes(
            @RequestParam
            @Parameter(description = "Json String Notes",required = true,
                    content = @Content(schema = @Schema(implementation = NotesRequest.class)))
            String notes,
            @RequestParam(required = false) MultipartFile file)
            throws ResourceNotFoundException, IOException;

    @Operation(summary = "Download file Endpoint", description = "Downloads the file attached to a specific note by its ID.")
    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) throws ResourceNotFoundException, IOException;

    @Operation(summary = "Get all notes Endpoint", description = "Retrieves a list of all notes from the database. This endpoint is accessible only to admin users.")
    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllNotes();

    @Operation(summary = "Get user notes Endpoint", description = "Retrieves a paginated list of notes created by the authenticated user.")
    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllNotesByUser(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @Operation(summary = "Search notes Endpoint", description = "Searches for notes created by the authenticated user that match the specified keyword in the title, category name, or description. The search results are paginated.")
    @GetMapping("/search-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> searchNotes(
            @RequestParam(name = "key") String key,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @Operation(summary = "Delete notes Endpoint", description = "Soft deletes a specific note by its ID.")
    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws ResourceNotFoundException;

    @Operation(summary = "Restore notes Endpoint", description = "Restores a soft-deleted note by its ID.")
    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws ResourceNotFoundException;

    @Operation(summary = "Get recycle bin notes Endpoint", description = "Retrieves a list of all soft-deleted notes created by the authenticated user.")
    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserRecycleBinNotes();

    @Operation(summary = "Hard delete notes Endpoint", description = "Permanently deletes a specific note by its ID from the database.")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws ResourceNotFoundException;

    @Operation(summary = "Empty recycle bin Endpoint", description = "Permanently deletes all soft-deleted notes created by the authenticated user from the database.")
    @DeleteMapping("/delete-recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> emptyUserRecycleBin();

    @Operation(summary = "Favorite note Endpoint", description = "Marks a specific note as a favorite for the authenticated user.")
    @GetMapping("/fav/{noteId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> favoriteNote(@PathVariable Integer noteId) throws ResourceNotFoundException;

    @Operation(summary = "Unfavorite note Endpoint", description = "Removes a specific note from the authenticated user's list of favorite notes.")
    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> unfavoriteNote(@PathVariable Integer favNoteId) throws ResourceNotFoundException;

    @Operation(summary = "Get favorite notes Endpoint", description = "Retrieves a list of all notes marked as favorites by the authenticated user.")
    @GetMapping("/fav-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserFavoriteNote();

    @Operation(summary = "Copy note Endpoint", description = "Creates a copy of a specific note by its ID for the authenticated user.")
    @GetMapping("/copy/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> copyNote(@PathVariable Integer id) throws ResourceNotFoundException;

}
