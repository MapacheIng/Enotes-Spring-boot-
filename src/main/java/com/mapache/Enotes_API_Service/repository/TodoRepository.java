package com.mapache.Enotes_API_Service.repository;

import com.mapache.Enotes_API_Service.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByCreatedBy(Integer createdBy);
}
