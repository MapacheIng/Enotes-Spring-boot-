package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.dto.CategoryDto;
import com.mapache.Enotes_API_Service.dto.NotesDto;
import com.mapache.Enotes_API_Service.entity.Category;
import com.mapache.Enotes_API_Service.entity.Notes;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.repository.CategoryRepository;
import com.mapache.Enotes_API_Service.repository.NotesRepository;
import com.mapache.Enotes_API_Service.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;


@Service
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;
    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;

    public NotesServiceImpl(NotesRepository notesRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.notesRepository = notesRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException {

        // category validation notes

        checkCategoryExist(notesDto.getCategory());

        Notes notes = mapper.map(notesDto, Notes.class);

        Notes saveNotes = notesRepository.save(notes);

        return !ObjectUtils.isEmpty(saveNotes);
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws ResourceNotFoundException {
        categoryRepository.findById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + category.getId()));
    }


    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll()
                .stream()
                .map(notes ->  mapper.map(notes, NotesDto.class))
                .toList();

    }
}
