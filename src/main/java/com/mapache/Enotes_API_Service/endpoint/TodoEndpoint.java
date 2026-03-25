package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.TodoDto;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.mapache.Enotes_API_Service.util.Constants.ROLE_USER;

@Tag(name = "Todo", description = "All the Todo operation APIs")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @Operation(summary = "Create a new Todo", description = "Create a new Todo for the authenticated user")
    @PostMapping
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws ResourceNotFoundException;

    @Operation(summary = "Get a Todo by ID", description = "Get a specific Todo by its ID for the authenticated user")
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodoById(@PathVariable Integer id) throws ResourceNotFoundException;

    @Operation(summary = "Get all Todos for the authenticated user", description = "Get a list of all Todos for the authenticated user")
    @GetMapping("/list")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllTodoByUser();

}
