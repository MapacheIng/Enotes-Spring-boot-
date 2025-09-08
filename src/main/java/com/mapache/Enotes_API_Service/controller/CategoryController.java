package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.CategoryDto;
import com.mapache.Enotes_API_Service.dto.CategoryResponse;
import com.mapache.Enotes_API_Service.entity.Category;
import com.mapache.Enotes_API_Service.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCategory = categoryService.saveCategory(categoryDto);
        if (!saveCategory) {
            return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @GetMapping("/active-category")
    public ResponseEntity<List<CategoryResponse>> getActiveCategory() {
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();
        if (CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }
}
