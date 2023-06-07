package com.tiendq.cinebooking.controller;


import com.tiendq.cinebooking.model.dtos.CategoryDTO;
import com.tiendq.cinebooking.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        log.warn("[CONTROLLER] - GET CATEGORY BY ID: " + id);
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategoryByUser() {
        log.warn("[CONTROLLER] - USER GET ALL CATEGORY");
        return ResponseEntity.ok(categoryService.getAllCategoryByUser());
    }

    @GetMapping("/management/categories")
    public ResponseEntity<Page<CategoryDTO>> getAllCategoryByAdmin(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "30") int size,
                                                                   @RequestParam(defaultValue = "name") String[] properties,
                                                                   @RequestParam(defaultValue = "ASC") Sort.Direction sort) {
        log.warn("[CONTROLLER] - ADMIN GET ALL CATEGORY");
        return ResponseEntity.ok(categoryService.getAllCategoryByAdmin(page, size, properties, sort));
    }

    @PostMapping("/management/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCategory(@RequestBody CategoryDTO categoryDTO) {
        log.warn("[CONTROLLER] - INSERT NEW CATEGORY: " + categoryDTO);
        categoryService.insertCategory(categoryDTO);
    }

    @PatchMapping("/management/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        log.warn("[CONTROLLER] - UPDATE CATEGORY: " + categoryDTO);
        categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/management/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        log.warn("[CONTROLLER] - DELETE CATEGORY BY ID: " + id);
        categoryService.deleteCategory(id);
    }
}
