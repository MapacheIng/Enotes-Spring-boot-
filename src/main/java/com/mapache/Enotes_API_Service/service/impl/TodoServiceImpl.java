package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.dto.TodoDto;
import com.mapache.Enotes_API_Service.entity.Todo;
import com.mapache.Enotes_API_Service.enums.TodoStatus;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.repository.TodoRepository;
import com.mapache.Enotes_API_Service.service.TodoService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import com.mapache.Enotes_API_Service.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {


    private final TodoRepository todoRepository;
    private final ModelMapper mapper;
    private final Validation validation;


    public TodoServiceImpl(TodoRepository todoRepository, ModelMapper mapper, Validation validation) {
        this.todoRepository = todoRepository;
        this.mapper = mapper;
        this.validation = validation;
    }

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws ResourceNotFoundException {
        validation.todoValidation(todoDto);
        Todo todo = mapper.map(todoDto, Todo.class);
        todo.setStatusId(todoDto.getStatus().getId());
        Todo saveTodo = todoRepository.save(todo);
        return !ObjectUtils.isEmpty(saveTodo);
    }

    @Override
    public TodoDto getTodoById(Integer id) throws ResourceNotFoundException {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found! id invalid"));
        TodoDto todoDto = mapper.map(todo, TodoDto.class);
        setStatus(todoDto, todo);
        return todoDto;
    }



    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId = CommonUtil.getLoggedInUser().getId();
        List<Todo> todos = todoRepository.findByCreatedBy(userId);
        return todos.stream()
                .map((td) -> {
                    TodoDto dto = mapper.map(td, TodoDto.class);
                    setStatus(dto, td);
                    return dto;
                })
                .toList();
    }

    private void setStatus(TodoDto todoDto, Todo todo) {
//        for (TodoStatus st:TodoStatus.values()){
//            if (st.getId().equals(todo.getStatusId())){
//                TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder()
//                        .id(st.getId())
//                        .name(st.getName())
//                        .build();
//                todoDto.setStatus(statusDto);
//            }
//        }

        TodoStatus st = TodoStatus.fromId(todo.getStatusId());
        todoDto.setStatus(TodoDto.StatusDto.builder()
                .id(st.getId())
                .name(st.getName())
                .build());
    }

}
