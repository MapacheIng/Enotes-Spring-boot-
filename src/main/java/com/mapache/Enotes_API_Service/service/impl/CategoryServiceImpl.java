package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.dto.CategoryDto;
import com.mapache.Enotes_API_Service.dto.CategoryResponse;
import com.mapache.Enotes_API_Service.entity.Category;
import com.mapache.Enotes_API_Service.exception.ExistDataException;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.repository.CategoryRepository;
import com.mapache.Enotes_API_Service.service.CategoryService;
import com.mapache.Enotes_API_Service.util.Validation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.module.ResolutionException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;
    private Validation validation;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        // validation check
        validation.categoryValidation(categoryDto);
        // validation check exists or not exists
        Boolean exist = categoryRepository.existsByName(categoryDto.getName().trim());
        if (exist){
            // throw error
            throw new ExistDataException("Category already exists with name = " + categoryDto.getName());
        }
        Category category = mapper.map(categoryDto, Category.class);
        if(ObjectUtils.isEmpty(category.getId())){
            category.setIsDeleted(false);
//            category.setCreatedBy(1);
//            category.setCreatedOn(new Date());
        } else {
            updateCategory(category);
        }
        Category saveCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(saveCategory);
    }

    private void updateCategory(Category category) {
        Optional<Category> findById = categoryRepository.findById(category.getId());
        if (findById.isPresent()){
            Category existingCategory = findById.get();
            category.setIsDeleted(existingCategory.getIsDeleted());

        }
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .toList();

        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(category -> mapper.map(category, CategoryResponse.class))
                .toList();

        return categoryResponses;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws Exception {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id=" + id));

        if (!ObjectUtils.isEmpty(category)) {
            category.getName().toUpperCase();
            return mapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<Category> findByCategory = categoryRepository.findById(id);
        if(findByCategory.isEmpty()){
            return false;
        }
        Category category = findByCategory.get();
        category.setIsDeleted(true);
        categoryRepository.save(category);
        return true;
    }
}
