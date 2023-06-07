package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.dtos.CategoryDTO;
import com.tiendq.cinebooking.model.entities.Category;
import com.tiendq.cinebooking.repository.CategoryRepository;
import com.tiendq.cinebooking.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getAllCategoryByUser() {
        log.warn("[SERVICE] - USER GET ALL CATEGORY");
        return categoryRepository
                .findAll()
                .stream()
                .map(c -> modelMapper.map(c, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDTO> getAllCategoryByAdmin(int page, int size, String[] properties, Sort.Direction sort) {
        log.warn("[SERVICE] - ADMIN GET ALL CATEGORY");
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, properties);
        if (sort.isDescending()) {
            pageable = PageRequest.of(page, size, Sort.Direction.DESC, properties);
        }
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(category -> modelMapper.map(category, CategoryDTO.class));
    }

    @Override
    public void insertCategory(CategoryDTO categoryDTO) {
        log.warn("[SERVICE] - INSERT NEW CATEGORY");
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long id, CategoryDTO categoryDTO) {
        log.warn("[SERVICE] - UPDATE CATEGORY");
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) throw new NotFoundException("not found category by id: " + id);
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        log.warn("[SERVICE] - DELETE CATEGORY");
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) throw new NotFoundException("not found category by id: " + id);
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        log.warn("[SERVICE] - GET CATEGORY BY ID: " + id);
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) throw new NotFoundException("not found category by id: " + id);
        return modelMapper.map(category, CategoryDTO.class);
    }
}
