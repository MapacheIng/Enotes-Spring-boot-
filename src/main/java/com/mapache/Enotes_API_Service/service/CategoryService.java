package com.mapache.Enotes_API_Service.service;

import com.mapache.Enotes_API_Service.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(Category category);
    public List<Category> getAllCategory();

}
