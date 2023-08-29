package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.category.Category;
import com.devcourse.kurlymurly.module.product.domain.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryRetrieve {
    private final CategoryRepository categoryRepository;

    public CategoryRetrieve(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findByIdOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
