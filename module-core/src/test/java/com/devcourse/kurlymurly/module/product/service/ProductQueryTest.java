package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;
import com.devcourse.kurlymurly.module.product.domain.favorite.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devcourse.kurlymurly.module.product.ProductFixture.LA_GOGI;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductQueryTest {
    @Autowired
    private ProductQuery productQuery;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    private Long userId = 1L;
    private Product product;
    private Favorite favorite;

    @BeforeEach
    void init() {
        product = productRepository.save(LA_GOGI.toEntity());
        favorite = favoriteRepository.save(new Favorite(userId, product));
    }

    @Test
    void getAllFavoritesByUserId_Success() {
        // given

        // when
        List<Favorite> result = productQuery.getAllFavoritesByUserId(userId);

        // then
        assertThat(result).isNotEmpty().hasSize(1);

        Favorite response = result.get(0);
        assertThat(response.getId()).isEqualTo(favorite.getId());
        assertThat(response.getProductName()).isEqualTo(product.getName());
        assertThat(response.getProductPrice()).isEqualTo(product.getPrice());
    }
}
