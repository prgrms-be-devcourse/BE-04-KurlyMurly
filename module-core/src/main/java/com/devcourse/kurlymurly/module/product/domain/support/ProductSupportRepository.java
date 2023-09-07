package com.devcourse.kurlymurly.module.product.domain.support;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductSupportRepository extends JpaRepository<ProductSupport, Long> {
    @Query("SELECT ps FROM ProductSupport ps WHERE ps.userId = :userId AND ps.status != 'DELETED' AND ps.id < :startId ORDER BY ps.id DESC LIMIT 10")
    Slice<ProductSupport> findTenByUserIdFromStartId(@Param("userId") Long userId, @Param("startId") Long startId);
}
