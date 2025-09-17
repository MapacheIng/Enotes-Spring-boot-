package com.mapache.Enotes_API_Service.repository;

import com.mapache.Enotes_API_Service.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

    // esto fue la primera version
//    Page<Notes> findByCreatedBy(Integer createdBy, Pageable pages);


    List<Notes> findByCreatedByAndIsDeletedTrue(Integer createdBy);

    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer id, Pageable pages);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(Boolean isDeleted, LocalDateTime deletedOnBefore);
}
