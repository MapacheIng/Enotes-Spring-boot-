package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Map<String,Object>> createCategory(@RequestBody CategoryDto categoryDto);

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Map<String,Object>> getAllCategory();

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<Map<String, Object>> getActiveCategory();

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<Map<String, Object>> getCategoryDetailsById(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Map<String, Object>> deleteCategoryById(@PathVariable Integer id);

}
