package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.dto.TodoDto;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;

import java.util.List;

public interface TodoService {

    Boolean saveTodo(TodoDto todoDto) throws ResourceNotFoundException;

    TodoDto getTodoById(Integer id) throws ResourceNotFoundException;

    List<TodoDto> getTodoByUser();

}
