package com.devcourse.kurlymurly.module.product.domain.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndProductId(Long userId, Long productId);

    @Query("SELECT f FROM Favorite f JOIN FETCH f.product WHERE f.userId = :userId AND f.status != 'DELETED' ORDER BY f.createAt")
    List<Favorite> findAllByUserId(@Param(value = "userId") Long userId);
}
