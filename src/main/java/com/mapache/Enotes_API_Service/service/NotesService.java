package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.dto.FavouriteNoteDto;
import com.mapache.Enotes_API_Service.dto.NotesDto;
import com.mapache.Enotes_API_Service.dto.NotesResponse;
import com.mapache.Enotes_API_Service.entity.FileDetails;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NotesService {

//    public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException;
    public Boolean saveNotes(String notes, MultipartFile file) throws ResourceNotFoundException, IOException;

    public List<NotesDto> getAllNotes();

    byte[] downloadFile(FileDetails fileDtls) throws IOException;

    FileDetails getFileDetails(Integer id) throws ResourceNotFoundException;

    NotesResponse getAllNotesByUser(Integer id, Integer pageNo, Integer pageSize);

    void softDeleteNotes(Integer id) throws ResourceNotFoundException;

    void restoreNotes(Integer id) throws ResourceNotFoundException;

    List<NotesDto> getUserRecycleBinNotes(Integer userId);

    void hardDeleteNotes(Integer id) throws ResourceNotFoundException;

    void emptyRecycleBin(Integer userId);

    void favoriteNotes(Integer noteId) throws ResourceNotFoundException;

    void unFavoriteNotes(Integer favoriteNoteId) throws ResourceNotFoundException;

    List<FavouriteNoteDto> getUserFavoriteNotes();

    boolean copyNotes(Integer id) throws ResourceNotFoundException;

}
