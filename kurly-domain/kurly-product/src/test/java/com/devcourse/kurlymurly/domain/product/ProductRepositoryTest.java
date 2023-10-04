package com.devcourse.kurlymurly.domain.product;

import com.devcourse.kurlymurly.web.common.KurlyPagingRequest;
import com.devcourse.kurlymurly.web.product.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Product product;

    @BeforeEach
    void init() {
        product = productRepository.save(ProductFixture.LA_GOGI.toEntity());
    }

    @Test
    @DisplayName("카테고리에 맞는 삭제되지 않은 상품들을 리뷰 수와 함께 GetProduct.SimpleResponse에 담아야 한다.")
    void loadProductsByCategory_Success() {
        // given
        Long categoryId = 1L;
        KurlyPagingRequest pagingRequest = new KurlyPagingRequest(1, null);
        Pageable request = pagingRequest.toPageable();

        // when
        Page<ProductResponse.GetSimple> responses = productRepository.loadProductsByCategory(categoryId, request);

        // then
        assertThat(responses).isNotEmpty().hasSize(1);

        ProductResponse.GetSimple response = responses.get().toList().get(0);
        assertThat(response.reviewCount()).isNotNull().isEqualTo(0);
    }

    @Test
    @DisplayName("삭제되지 않은 일주일 내의 상품들을 리뷰 수와 함께 GetProduct.SimpleResponse에 담아야 한다.")
    void loadNewProducts_Success() {
        // given
        KurlyPagingRequest pagingRequest = new KurlyPagingRequest(1, null);
        Pageable request = pagingRequest.toPageable();

        entityManager.clear();

        // when
        Page<ProductResponse.GetSimple> responses = productRepository.loadNewProducts(request);

        // then
        assertThat(responses).isNotEmpty().hasSize(1);

        ProductResponse.GetSimple response = responses.get().toList().get(0);
        assertThat(response.reviewCount()).isNotNull().isEqualTo(0);
    }
}
