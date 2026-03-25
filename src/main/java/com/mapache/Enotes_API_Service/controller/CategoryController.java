package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.dto.CategoryDto;
import com.mapache.Enotes_API_Service.dto.CategoryResponse;
import com.mapache.Enotes_API_Service.endpoint.CategoryEndpoint;
import com.mapache.Enotes_API_Service.service.CategoryService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class CategoryController implements CategoryEndpoint {

    private CategoryService categoryService;

    @Override
    public ResponseEntity<Map<String,Object>> createCategory(CategoryDto categoryDto){
        Boolean saveCategory = categoryService.saveCategory(categoryDto);
        if (!saveCategory) {
            return CommonUtil.createErrorResponseMessage("Category Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuilderResponseMessage("Category saved successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String,Object>> getAllCategory() {
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(allCategory, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getActiveCategory() {
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();
        if (CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(allCategory, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getCategoryDetailsById(Integer id) throws Exception {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (ObjectUtils.isEmpty(categoryDto)) {
            return CommonUtil.createErrorResponseMessage("Category Not Found with Id= " + id, HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuilderResponse(categoryDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleteCategoryById(Integer id){
        Boolean deleted = categoryService.deleteCategory(id);
        if (!deleted) {
            return CommonUtil.createErrorResponseMessage("Category Not Deleted", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuilderResponseMessage("Category Deleted success", HttpStatus.OK);
    }



}
