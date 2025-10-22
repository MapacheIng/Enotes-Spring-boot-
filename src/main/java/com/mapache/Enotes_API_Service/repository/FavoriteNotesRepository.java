package com.mapache.Enotes_API_Service.repository;

import com.mapache.Enotes_API_Service.entity.FavouriteNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteNotesRepository extends JpaRepository<FavouriteNote, Integer> {

    List<FavouriteNote> findByUserId(Integer userId);

}