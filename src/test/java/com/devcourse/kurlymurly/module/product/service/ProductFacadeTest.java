package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.category.Category;
import com.devcourse.kurlymurly.module.product.domain.support.ProductSupport;
import com.devcourse.kurlymurly.web.dto.CreateProduct;
import com.devcourse.kurlymurly.web.dto.SupportProduct;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.devcourse.kurlymurly.module.product.ProductFixture.*;
import static com.devcourse.kurlymurly.module.product.ProductSupportFixture.*;
import static com.devcourse.kurlymurly.module.product.domain.Product.Status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class ProductFacadeTest {
    @InjectMocks
    private ProductFacade productFacade;

    @Mock
    private ProductCreate productCreate;

    @Mock
    private ProductRetrieve productRetrieve;

    @Mock
    private CategoryRetrieve categoryRetrieve;

    @Mock
    private ProductSupportCreate productSupportCreate;

    @Mock
    private ProductSupportRetrieve productSupportRetrieve;

    @Nested
    class createTest {
        @Test
        @DisplayName("상품 생성 요청이 들어오면 데이터가 저장되고 응답이 잘 생성된다.")
        void createProduct_Success() {
            // given
            Category category = new Category("hi", "hello");
            CreateProduct.Request request = LA_GOGI.toRequest();

            given(categoryRetrieve.findByIdOrThrow(any())).willReturn(category);
            willDoNothing().given(productCreate).create(any());

            // when
            CreateProduct.Response response = productFacade.createProduct(request);

            // then
            then(categoryRetrieve).should(times(1)).findByIdOrThrow(any());
            then(productCreate).should(times(1)).create(any());
            assertThat(response.categoryName()).isEqualTo(category.getName());
            assertThat(response.productName()).isEqualTo(request.name());
        }

        @Test
        @DisplayName("존재하지 않는 카테고리로 생성 요청을 하면 EntityNotFoundException을 던진다.")
        void createProduct_Fail_ByNotExistCategory() {
            // given
            CreateProduct.Request request = LA_GOGI.toRequest();
            given(categoryRetrieve.findByIdOrThrow(any())).willThrow(EntityNotFoundException.class);

            // when, then
            then(productCreate).shouldHaveNoInteractions();
            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(() -> productFacade.createProduct(request));
        }
    }

    @Nested
    class deleteTest {
        private final Long productId = 1L;

        @Test
        @DisplayName("삭제 요청을 하면 상태가 DELETED로 변경된다.")
        void delete_Success() {
            // given
            Product product = LA_GOGI.toEntity();
            given(productRetrieve.findByIdOrThrow(any())).willReturn(product);

            // when
            productFacade.delete(productId);

            // then
            then(productRetrieve).should(times(1)).findByIdOrThrow(any());
            assertThat(product.getStatus()).isEqualTo(Status.DELETED);
        }

        @Test
        @DisplayName("존재하지 않는 상품을 접근하면 EntityNotFoundException을 던진다.")
        void delete_Fail_ByNotExistProduct() {
            // given
            given(productRetrieve.findByIdOrThrow(any())).willThrow(EntityNotFoundException.class);

            // when, then
            assertThatExceptionOfType(EntityNotFoundException.class)
                    .isThrownBy(() -> productFacade.delete(productId));
        }
    }

    @Nested
    class createProductSupportTest {
        private final Long userId = 1L;
        private final Long productId = 1L;
        private final SupportProduct.Request request = SUPPORT_FIXTURE.toRequest();

        @Test
        @DisplayName("상품 문의를 생성하면 유효성 검사를 통과하고 생성 호출이 정상적으로 이루어진다.")
        void createProductSupport_Success() {
            // given
            Product product = LA_GOGI.toEntity();

            given(productRetrieve.findByIdOrThrow(any())).willReturn(product);
            willDoNothing().given(productSupportCreate).create(any(), any(), any());

            // when
            productFacade.createProductSupport(userId, productId, request);

            // then
            then(productSupportCreate).should(times(1)).create(any(), any(), any());
        }

        @Test
        @DisplayName("삭제된 상품에 리뷰를 남기려고 하면 IllegalStateException을 던진다.")
        void createProductSupport_Fail_ByDeletedProduct() {
            // given
            Product product = LA_GOGI.toEntity();
            product.softDelete();

            given(productRetrieve.findByIdOrThrow(any())).willReturn(product);

            // when, then
            assertThatExceptionOfType(IllegalStateException.class)
                    .isThrownBy(() -> productFacade.createProductSupport(userId, productId, request));
            then(productSupportCreate).shouldHaveNoInteractions();
        }
    }

    @Nested
    class updateProductSupportTest {
        private final Long userId = SUPPORT_FIXTURE.getUserId();
        private final SupportProduct.Request request = SECRET_SUPPORT_FIXTURE.toRequest();

        @Test
        @DisplayName("상품 문의를 비밀글로 수정 요청이 들어오면 반영이 잘되어야 한다.")
        void updateProductSupport_Success() {
            // given
            ProductSupport support = SUPPORT_FIXTURE.toEntity();
            given(productSupportRetrieve.findByIdOrThrow(any())).willReturn(support);

            // when
            productFacade.updateProductSupport(userId, support.getId(), request);

            // then
            then(productSupportRetrieve).should(times(1)).findByIdOrThrow(any());
            assertThat(support.isSecret()).isTrue();
        }

        @Test
        @DisplayName("다른 사람이 상품 문의를 수정하려고 하면 IllegalArgumentException을 던진다.")
        void updateProductSupport_Fail_ByNotMatchAuthor() {
            // given
            ProductSupport support = SUPPORT_FIXTURE.toEntity();
            given(productSupportRetrieve.findByIdOrThrow(any())).willReturn(support);

            // when, then
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> productFacade.updateProductSupport(
                            SECRET_SUPPORT_FIXTURE.getUserId(),
                            support.getId(),
                            request)
                    );
        }
    }
}
