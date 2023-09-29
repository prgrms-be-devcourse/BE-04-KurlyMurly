package com.devcourse.kurlymurly.module.product.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsById(Long id);
}
