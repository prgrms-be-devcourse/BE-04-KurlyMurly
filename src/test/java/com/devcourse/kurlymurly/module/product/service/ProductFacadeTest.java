package com.devcourse.kurlymurly.module.product.service;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.category.Category;
import com.devcourse.kurlymurly.module.product.domain.favorite.Favorite;
import com.devcourse.kurlymurly.module.product.domain.favorite.FavoriteRepository;
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

import java.util.Optional;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NEVER_FAVORITE;
import static com.devcourse.kurlymurly.module.product.FavoriteFixture.FAVORITE_FIXTURE;
import static com.devcourse.kurlymurly.module.product.ProductFixture.LA_GOGI;
import static com.devcourse.kurlymurly.module.product.ProductSupportFixture.SECRET_SUPPORT_FIXTURE;
import static com.devcourse.kurlymurly.module.product.ProductSupportFixture.SUPPORT_FIXTURE;
import static com.devcourse.kurlymurly.module.product.domain.Product.Status;
import static com.devcourse.kurlymurly.module.product.domain.Product.Status.SOLD_OUT;
import static com.devcourse.kurlymurly.module.product.domain.favorite.Favorite.Status.DELETED;
import static com.devcourse.kurlymurly.module.product.domain.favorite.Favorite.Status.NORMAL;
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

    @Mock
    private FavoriteRepository favoriteRepository;

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
        @DisplayName("존재하지 않는 상품을 접근하면 예외을 던진다.")
        void delete_Fail_ByNotExistProduct() {
            // given
            given(productRetrieve.findByIdOrThrow(any())).willThrow(KurlyBaseException.class);

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> productFacade.delete(productId));
        }
    }

    @Nested
    @DisplayName("상품 품절 테스트")
    class soldOutProductTest {
        @Test
        @DisplayName("관리자가 상품을 품절 처리하면 품절 상태로 바뀐다.")
        void soldOutProduct_Success() {
            // given
            Product product = LA_GOGI.toEntity();
            given(productRetrieve.findByIdOrThrow(any())).willReturn(product);

            // when
            productFacade.soldOutProduct(product.getId());

            // then
            then(productRetrieve).should(times(1)).findByIdOrThrow(any());
            assertThat(product.getStatus()).isEqualTo(SOLD_OUT);
        }

        @Test
        @DisplayName("삭제된 상품을 품절 상태로 변경하면 예외를 던진다.")
        void soldOutProduct_Fail_ByDeletedProduct() {
            // given
            Product product = LA_GOGI.toEntity();
            product.softDelete();

            given(productRetrieve.findByIdOrThrow(any())).willReturn(product);

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> productFacade.soldOutProduct(product.getId()));
            then(productRetrieve).should(times(1)).findByIdOrThrow(any());
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
        @DisplayName("삭제된 상품에 리뷰를 남기려고 하면 예외를 던진다.")
        void createProductSupport_Fail_ByDeletedProduct() {
            // given
            Product product = LA_GOGI.toEntity();
            product.softDelete();

            given(productRetrieve.findByIdOrThrow(any())).willReturn(product);

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
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

    @Nested
    @DisplayName("상품 찜 테스트")
    class favoriteTest {
        @Test
        @DisplayName("이미 생성된 찜이 있다면 해당 찜을 활성화 시킨다.")
        void favoriteProduct_Success_WithSavedFavorite() {
            // given
            Favorite favorite = FAVORITE_FIXTURE.toEntity();
            favorite.softDelete();

            given(favoriteRepository.findByUserIdAndProductId(any(), any())).willReturn(Optional.of(favorite));

            // when
            productFacade.favoriteProduct(FAVORITE_FIXTURE.userId(), FAVORITE_FIXTURE.productId());

            // then
            then(favoriteRepository).should(times(1)).findByUserIdAndProductId(any(), any());
            then(favoriteRepository).shouldHaveNoMoreInteractions();
            assertThat(favorite.getStatus()).isEqualTo(NORMAL);
        }

        @Test
        @DisplayName("저장된 찜이 없다면 새로운 찜을 만들어낸다.")
        void favoriteProduct_Success_WithNewFavorite() {
            // given
            Favorite favorite = FAVORITE_FIXTURE.toEntity();

            given(favoriteRepository.findByUserIdAndProductId(any(), any())).willReturn(Optional.empty());
            given(favoriteRepository.save(any())).willReturn(favorite);

            // when
            productFacade.favoriteProduct(FAVORITE_FIXTURE.userId(), FAVORITE_FIXTURE.productId());

            // then
            then(favoriteRepository).should(times(1)).findByUserIdAndProductId(any(), any());
            then(favoriteRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("삭제 요청이 들어오면 찜의 상태가 DELETED로 변경된다.")
        void cancelFavorite_Success() {
            // given
            Favorite favorite = FAVORITE_FIXTURE.toEntity();
            given(favoriteRepository.findByUserIdAndProductId(any(), any())).willReturn(Optional.of(favorite));

            // when
            productFacade.cancelFavorite(FAVORITE_FIXTURE.userId(), FAVORITE_FIXTURE.productId());

            // then
            then(favoriteRepository).should(times(1)).findByUserIdAndProductId(any(), any());
            assertThat(favorite.getStatus()).isEqualTo(DELETED);
        }

        @Test
        @DisplayName("존재하지 않는 찜을 취소하려고 하면 예외가 발생한다.")
        void cancelFavorite_Fail_ByNotExistFavorite() {
            // given
            given(favoriteRepository.findByUserIdAndProductId(any(), any())).willReturn(Optional.empty());

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> productFacade.cancelFavorite(FAVORITE_FIXTURE.userId(), FAVORITE_FIXTURE.productId()))
                    .withMessage(NEVER_FAVORITE.getMessage());
        }
    }
}
