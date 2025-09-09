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
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        Category category = mapper.map(categoryDto, Category.class);

        if(ObjectUtils.isEmpty(category.getId())){
            category.setIsDeleted(false);
            category.setCreatedBy(1);
            category.setCreatedOn(new Date());
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
            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setCreatedOn(existingCategory.getCreatedOn());
            category.setIsDeleted(existingCategory.getIsDeleted());

            category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());
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
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> findByCategory = categoryRepository.findByIdAndIsDeletedFalse(id);
        if(findByCategory.isEmpty()){
            return null;
        }
        Category category = findByCategory.get();
        return mapper.map(category, CategoryDto.class);
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
