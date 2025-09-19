package com.mapache.Enotes_API_Service.scheduler;

import com.mapache.Enotes_API_Service.entity.FileDetails;
import com.mapache.Enotes_API_Service.entity.Notes;
import com.mapache.Enotes_API_Service.repository.FileRepository;
import com.mapache.Enotes_API_Service.repository.NotesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class NotesSchedular {

    private final NotesRepository notesRepository;
    private final FileRepository fileRepository;

    public NotesSchedular(NotesRepository notesRepository, FileRepository fileRepository) {
        this.notesRepository = notesRepository;
        this.fileRepository = fileRepository;
    }

    @Scheduled(cron = "0 0 0 1/1 * ?", zone = "America/Bogota") // Runs every day at midnight
    public void deleteNotesSchedular() {
        // Logic to delete old notes
        LocalDateTime cutOfDay = LocalDateTime.now().minusDays(7);
        List<Notes> deletedNotes = notesRepository.findAllByIsDeletedAndDeletedOnBefore(true, cutOfDay);
        // Delete associated files
        List<FileDetails> deletedFile = deletedNotes.stream()
                .map(Notes::getFileDetails)
                .filter(Objects::nonNull)
                .toList();

        notesRepository.deleteAll(deletedNotes);
        fileRepository.deleteAll(deletedFile);

        // Delete files from filesystem
        if(!CollectionUtils.isEmpty(deletedFile)){
            deletedFile.stream()
                    .map(FileDetails::getPath)
                    .forEach(path -> {
                        try { Files.deleteIfExists(Paths.get(path)); }
                        catch (IOException e) { throw new RuntimeException(e); }
                    });
        }




    }

}
