package com.mapache.Enotes_API_Service.endpoint;

import com.mapache.Enotes_API_Service.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.mapache.Enotes_API_Service.util.Constants.ROLE_ADMIN;
import static com.mapache.Enotes_API_Service.util.Constants.ROLE_ADMIN_USER;

import java.util.Map;

@Tag(name = "Category", description = "All the Category operation APIs")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @Operation(summary = "Save category Endpoint", description = "Saves a new category to the database.")
    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<Map<String,Object>> createCategory(@RequestBody CategoryDto categoryDto);

    @Operation(summary = "Get all category Endpoint", description = "Retrieves a list of all categories from the database.")
    @GetMapping()
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<Map<String,Object>> getAllCategory();

    @Operation(summary = "Get active category Endpoint", description = "Retrieves a list of all active categories from the database.")
    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<Map<String, Object>> getActiveCategory();

    @Operation(summary = "Get category details Endpoint", description = "Retrieves the details of a specific category by its ID.")
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<Map<String, Object>> getCategoryDetailsById(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Delete category Endpoint", description = "Deletes a specific category by its ID.")
    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<Map<String, Object>> deleteCategoryById(@PathVariable Integer id);

}
