package com.devcourse.kurlymurly.module.product.domain.favorite;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductRepository;
import com.devcourse.kurlymurly.web.dto.product.favorite.FavoriteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.devcourse.kurlymurly.module.product.ProductFixture.LA_GOGI;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductRepository productRepository;

    private Long userId = 1L;
    private Product product;

    @BeforeEach
    void initProduct() {
        product = productRepository.save(LA_GOGI.toEntity());
    }

    @Test
    @DisplayName("유저 ID와 상품 ID로 저장된 favorite을 찾을 수 있다.")
    void findByUserIdAndProductId() {
        // given
        Favorite favorite = new Favorite(userId, product);
        favoriteRepository.save(favorite);

        // when
        Optional<Favorite> optionalFavorite = favoriteRepository.findByUserIdAndProductId(userId, product.getId());

        // then
        assertThat(optionalFavorite).isNotEmpty();
        assertThat(optionalFavorite.get()).usingRecursiveComparison().isEqualTo(favorite);
    }
    
    @Test
    @DisplayName("user에 맞는 찜 목록만 가져올 수 있어야 한다.")
    void findAllByUserIdOrderByCreateAt() {
        // given
        Favorite target = new Favorite(userId, product);
        Favorite nonTarget = new Favorite(2L, product);
        favoriteRepository.saveAll(List.of(target, nonTarget));
        
        // when
        List<FavoriteResponse.Get> result = favoriteRepository.findAllByUserId(userId);

        // then
        assertThat(result).isNotEmpty().hasSize(1);

        FavoriteResponse.Get response = result.get(0);
        assertThat(response.productId()).isEqualTo(product.getId());
        assertThat(response.productName()).isEqualTo(product.getName());
        assertThat(response.price()).isEqualTo(product.getPrice());
    }
}
