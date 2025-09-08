package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.dto.CategoryDto;
import com.mapache.Enotes_API_Service.dto.CategoryResponse;
import com.mapache.Enotes_API_Service.entity.Category;
import com.mapache.Enotes_API_Service.repository.CategoryRepository;
import com.mapache.Enotes_API_Service.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.setIsActive(categoryDto.getIsActive());

        Category category = mapper.map(categoryDto, Category.class);

        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category saveCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(saveCategory);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .toList();

        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrue();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(category -> mapper.map(category, CategoryResponse.class))
                .toList();

        return categoryResponses;
    }
}
