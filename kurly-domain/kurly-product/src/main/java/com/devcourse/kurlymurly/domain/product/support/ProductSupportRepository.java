package com.devcourse.kurlymurly.domain.product.support;

import com.devcourse.kurlymurly.web.product.SupportResponse;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductSupportRepository extends JpaRepository<ProductSupport, Long> {
    @Query("""
           SELECT NEW com.devcourse.kurlymurly.web.product.SupportResponse$Create(
                ps.id, ps.productId, ps.productName, ps.title, ps.content, ps.isSecret, CAST(ps.status AS STRING), ps.createAt
           )
           FROM ProductSupport ps
           WHERE ps.userId = :userId
           AND ps.status != 'DELETED' AND ps.id < :startId
           ORDER BY ps.id DESC
           LIMIT 10
           """)
    Slice<SupportResponse.Create> findTenByUserIdFromStartId(@Param("userId") Long userId, @Param("startId") Long startId);
}
