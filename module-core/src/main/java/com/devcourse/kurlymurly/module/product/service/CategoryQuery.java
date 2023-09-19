package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.CATEGORY_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class CategoryQuery {
    private final CategoryRepository categoryRepository;

    public CategoryQuery(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void validateIsExist(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw KurlyBaseException.withId(CATEGORY_NOT_FOUND, id);
        }
    }
}
