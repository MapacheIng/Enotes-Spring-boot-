package com.mapache.Enotes_API_Service.scheduler;

import com.mapache.Enotes_API_Service.entity.Notes;
import com.mapache.Enotes_API_Service.repository.NotesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesSchedular {

    private final NotesRepository notesRepository;

    public NotesSchedular(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Scheduled(cron = "0 0 0 1/1 * ?", zone = "America/Bogota") // Runs every day at midnight
    public void deleteNotesSchedular() {
        // Logic to delete old notes
        LocalDateTime cutOfDay = LocalDateTime.now().minusDays(7);
        List<Notes> deletedNotes = notesRepository.findAllByIsDeletedAndDeletedOnBefore(true, cutOfDay);
        notesRepository.deleteAll(deletedNotes);


    }

}
