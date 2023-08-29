package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.module.product.ProductFixture;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.category.Category;
import com.devcourse.kurlymurly.web.dto.CreateProduct;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Nested
    class createTest {
        @Test
        @DisplayName("상품 생성 요청이 들어오면 데이터가 저장되고 응답이 잘 생성된다.")
        void createProduct_Success() {
            // given
            Category category = new Category("hi", "hello");
            CreateProduct.Request request = ProductFixture.LA_GOGI.getCreateRequest();

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
            CreateProduct.Request request = ProductFixture.LA_GOGI.getCreateRequest();
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
            Product product = ProductFixture.LA_GOGI.getProduct();
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
}
