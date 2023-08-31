package com.devcourse.kurlymurly.module.product.domain.favorite;

import com.devcourse.kurlymurly.module.product.FavoriteFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;

    private final FavoriteFixture fixture = FavoriteFixture.FAVORITE_FIXTURE;

    @Test
    @DisplayName("유저 ID와 상품 ID로 저장된 favorite을 찾을 수 있다.")
    void findByUserIdAndProductId() {
        // given
        Favorite favorite = fixture.toEntity();
        favoriteRepository.save(favorite);

        // when
        Optional<Favorite> optionalFavorite = favoriteRepository.findByUserIdAndProductId(fixture.userId(), fixture.productId());

        // then
        assertThat(optionalFavorite).isNotEmpty();
        assertThat(optionalFavorite.get()).usingRecursiveComparison().isEqualTo(favorite);
    }
}
