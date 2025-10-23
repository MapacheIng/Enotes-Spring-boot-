package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.TodoDto;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.service.TodoService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {


    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws ResourceNotFoundException {
        Boolean saveTodo = todoService.saveTodo(todoDto);
        if (!saveTodo) {
            return CommonUtil.createErrorResponseMessage("Todo not saved successfully", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuilderResponseMessage("Todo saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodo(@PathVariable Integer id) throws ResourceNotFoundException {
        TodoDto todoById = todoService.getTodoById(id);
        return CommonUtil.createBuilderResponse(todoById, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllTodoByUser() throws ResourceNotFoundException {
        List<TodoDto> todoList = todoService.getTodoByUser();
        if (CollectionUtils.isEmpty(todoList)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(todoList, HttpStatus.OK);
    }

}
