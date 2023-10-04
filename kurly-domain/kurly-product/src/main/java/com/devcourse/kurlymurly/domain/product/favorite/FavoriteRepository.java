package com.devcourse.kurlymurly.domain.product.favorite;

import com.devcourse.kurlymurly.web.product.FavoriteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndProductId(Long userId, Long productId);

    @Query("""
            SELECT NEW com.devcourse.kurlymurly.web.product.FavoriteResponse$Get(
                p.id, p.imageUrl, p.name, p.price
            )
            FROM Favorite f
            LEFT JOIN f.product p
            WHERE f.userId = :userId AND f.isDeleted = FALSE
            ORDER BY f.createAt DESC
            """)
    List<FavoriteResponse.Get> findAllByUserId(@Param("userId") Long userId);
}
