package com.devcourse.kurlymurly.domain.product;

import com.devcourse.kurlymurly.web.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            SELECT NEW com.devcourse.kurlymurly.web.product.ProductResponse$GetSimple(
                p.imageUrl, CAST(p.delivery AS STRING), p.name, p.description, p.price, COALESCE(r.reviewCount, 0), p.isKurlyOnly, CAST(p.status AS STRING)
            )
            FROM Product p
            LEFT JOIN (
                SELECT r.productId AS productId, COUNT(*) AS reviewCount
                FROM Review r
                WHERE r.status IN ('NORMAL', 'BEST')
                GROUP BY r.productId
            ) r ON p.id = r.productId
            WHERE p.categoryId = :categoryId
            AND p.status != 'DELETED'
            """)
    Page<ProductResponse.GetSimple> loadProductsByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("""
           SELECT NEW com.devcourse.kurlymurly.web.product.ProductResponse$GetSimple(
               p.imageUrl, CAST(p.delivery AS STRING), p.name, p.description, p.price, COALESCE(r.reviewCount, 0), p.isKurlyOnly, CAST(p.status AS STRING)
           )
           FROM Product p
           LEFT JOIN (
               SELECT r.productId AS productId, COUNT(*) AS reviewCount
               FROM Review r
               WHERE r.status IN ('NORMAL', 'BEST')
               GROUP BY r.productId
           ) r ON p.id = r.productId
           WHERE p.createAt >= CURRENT_DATE - 7
           AND p.status != 'DELETED'
           """)
    Page<ProductResponse.GetSimple> loadNewProducts(Pageable pageable);
}
