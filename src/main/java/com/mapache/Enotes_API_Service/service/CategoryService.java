package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.dto.CategoryDto;
import com.mapache.Enotes_API_Service.dto.CategoryResponse;
import com.mapache.Enotes_API_Service.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();

    List<CategoryResponse> getActiveCategory();
}
