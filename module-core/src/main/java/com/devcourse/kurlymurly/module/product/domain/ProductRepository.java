package com.devcourse.kurlymurly.module.product.domain;

import com.devcourse.kurlymurly.web.dto.product.GetProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            SELECT NEW com.devcourse.kurlymurly.web.dto.product.GetProduct$SimpleResponse(
               p.imageUrl, p.delivery, p.name, p.description, p.price, COALESCE(r.reviewCount, 0), p.isKurlyOnly, p.status
            )
            FROM Product p
            LEFT JOIN (
                SELECT r.product.id AS productId, COUNT(*) AS reviewCount
                FROM Review r
                WHERE r.status IN ('NORMAL', 'BEST')
                GROUP BY r.product.id
            ) r ON p.id = r.productId
            WHERE p.categoryId = :categoryId AND p.status != 'DELETED'
            """)
    Page<GetProduct.SimpleResponse> loadProductsByCategory(@Param("categoryId") Long categoryId, Pageable pageable);
}
