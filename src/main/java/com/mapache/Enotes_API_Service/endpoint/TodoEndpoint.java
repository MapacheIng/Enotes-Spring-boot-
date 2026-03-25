package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.TodoDto;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.mapache.Enotes_API_Service.util.Constants.ROLE_USER;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @PostMapping
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws ResourceNotFoundException;

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodoById(@PathVariable Integer id) throws ResourceNotFoundException;

    @GetMapping("/list")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllTodoByUser();

}
