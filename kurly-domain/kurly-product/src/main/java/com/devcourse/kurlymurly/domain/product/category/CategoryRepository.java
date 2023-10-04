package com.devcourse.kurlymurly.domain.product.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsById(Long id);
}
