package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.dtos.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryDTO> getAllCategoryByUser();

    Page<CategoryDTO> getAllCategoryByAdmin(int page, int size, String[] properties, Sort.Direction sort);

    void insertCategory(CategoryDTO categoryDTO);

    void updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    CategoryDTO getCategoryById(Long id);
}
