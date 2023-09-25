package com.devcourse.kurlymurly.module.product.domain.support;

import com.devcourse.kurlymurly.web.product.SupportResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Slice;

import java.util.List;

import static com.devcourse.kurlymurly.module.product.ProductSupportFixture.SECRET_SUPPORT_FIXTURE;
import static com.devcourse.kurlymurly.module.product.ProductSupportFixture.SUPPORT_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductSupportRepositoryTest {
    @Autowired
    private ProductSupportRepository productSupportRepository;

    @Test
    void findTenByUserIdFromStartId() {
        // given
        Long userId = 1L;
        ProductSupport targetSupport = SUPPORT_FIXTURE.toEntity();
        ProductSupport nonTargetSupport = SECRET_SUPPORT_FIXTURE.toEntity();
        productSupportRepository.saveAll(List.of(targetSupport, nonTargetSupport));

        // when
        Slice<SupportResponse.Create> result = productSupportRepository.findTenByUserIdFromStartId(userId, 10L);

        // then
        assertThat(result).isNotEmpty().hasSize(1);

        SupportResponse.Create response = result.get().toList().get(0);
        assertThat(response.productId()).isEqualTo(targetSupport.getProductId());
        assertThat(response.productName()).isEqualTo(targetSupport.getProductName());
        assertThat(response.status()).isEqualTo(targetSupport.getStatus().name());
        assertThat(response.isSecret()).isFalse();
    }
}
