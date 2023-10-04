package com.devcourse.kurlymurly.domain.product;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.product.favorite.Favorite;
import com.devcourse.kurlymurly.domain.product.favorite.FavoriteRepository;
import com.devcourse.kurlymurly.domain.product.support.ProductSupport;
import com.devcourse.kurlymurly.domain.product.support.ProductSupportRepository;
import com.devcourse.kurlymurly.domain.service.CategoryQuery;
import com.devcourse.kurlymurly.domain.service.ProductCommand;
import com.devcourse.kurlymurly.domain.service.ProductQuery;
import com.devcourse.kurlymurly.web.product.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.NEVER_FAVORITE;
import static com.devcourse.kurlymurly.domain.product.Product.Status.DELETED;
import static com.devcourse.kurlymurly.domain.product.Product.Status.SOLD_OUT;
import static com.devcourse.kurlymurly.domain.product.ProductFixture.LA_GOGI;
import static com.devcourse.kurlymurly.domain.product.ProductSupportFixture.SECRET_SUPPORT_FIXTURE;
import static com.devcourse.kurlymurly.domain.product.ProductSupportFixture.SUPPORT_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductCommandTest {
    @InjectMocks
    private ProductCommand productCommand;

    @Mock
    private CategoryQuery categoryQuery;

    @Mock
    private ProductQuery productQuery;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductSupportRepository productSupportRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Nested
    class createTest {
        private final ProductRequest.Create request = LA_GOGI.toRequest();
        private final String imageUrl = "";

        @Test
        @DisplayName("상품 생성 요청이 들어오면 데이터가 저장된다.")
        void createProduct_Success() {
            // given
            willDoNothing().given(categoryQuery).validateIsExist(any());

            // when
            productCommand.create(request.categoryId(), imageUrl, LA_GOGI.toDomain());

            // then
            then(categoryQuery).should(times(1)).validateIsExist(any());
            then(productRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("존재하지 않는 카테고리로 생성 요청을 하면 예외을 던진다.")
        void createProduct_Fail_ByNotExistCategory() {
            // given
            willThrow(KurlyBaseException.class).given(categoryQuery).validateIsExist(any());

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> productCommand.create(request.categoryId(), imageUrl, LA_GOGI.toDomain()));
            then(productRepository).shouldHaveNoInteractions();
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
            given(productQuery.findProductByIdOrThrow(any())).willReturn(product);

            // when
            productCommand.deleteProduct(productId);

            // then
            then(productQuery).should(times(1)).findProductByIdOrThrow(any());
            assertThat(product.getStatus()).isEqualTo(DELETED);
        }

        @Test
        @DisplayName("존재하지 않는 상품을 접근하면 예외을 던진다.")
        void delete_Fail_ByNotExistProduct() {
            // given
            given(productQuery.findProductByIdOrThrow(any())).willThrow(KurlyBaseException.class);

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> productCommand.deleteProduct(productId));
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
            given(productQuery.findProductByIdOrThrow(any())).willReturn(product);

            // when
            productCommand.soldOutProduct(product.getId());

            // then
            then(productQuery).should(times(1)).findProductByIdOrThrow(any());
            assertThat(product.getStatus()).isEqualTo(SOLD_OUT);
        }

        @Test
        @DisplayName("삭제된 상품을 품절 상태로 변경하면 예외를 던진다.")
        void soldOutProduct_Fail_ByDeletedProduct() {
            // given
            Product product = LA_GOGI.toEntity();
            product.softDelete();

            given(productQuery.findProductByIdOrThrow(any())).willReturn(product);

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> productCommand.soldOutProduct(product.getId()));
            then(productQuery).should(times(1)).findProductByIdOrThrow(any());
        }
    }

    @Nested
    class createProductSupportTest {
        private final Long userId = 1L;
        private final Long productId = 1L;
        private final SupportDomain domain = SUPPORT_FIXTURE.toDomain();

        @Test
        @DisplayName("상품 문의를 생성하면 유효성 검사를 통과하고 생성 호출이 정상적으로 이루어진다.")
        void createProductSupport_Success() {
            // given
            Product product = LA_GOGI.toEntity();
            given(productQuery.findProductByIdOrThrow(any())).willReturn(product);

            // when
            productCommand.createSupport(userId, productId, domain);

            // then
            then(productQuery).should(times(1)).findProductByIdOrThrow(any());
        }

        @Test
        @DisplayName("삭제된 상품에 리뷰를 남기려고 하면 예외를 던진다.")
        void createProductSupport_Fail_ByDeletedProduct() {
            // given
            Product product = LA_GOGI.toEntity();
            product.softDelete();

            given(productQuery.findProductByIdOrThrow(any())).willReturn(product);

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> productCommand.createSupport(userId, productId, domain));
            then(productSupportRepository).shouldHaveNoInteractions();
        }
    }

    @Nested
    class updateProductSupportTest {
        private final Long userId = SUPPORT_FIXTURE.getUserId();
        private final ProductSupportFixture fixture = SUPPORT_FIXTURE;

        @Test
        @DisplayName("상품 문의를 비밀글로 수정 요청이 들어오면 반영이 잘되어야 한다.")
        void updateProductSupport_Success() {
            // given
            ProductSupport support = fixture.toEntity();
            given(productQuery.findSupportByIdOrThrow(any())).willReturn(support);

            // when
            productCommand.updateSupport(userId, support.getId(), fixture.toSecretDomain());

            // then
            then(productQuery).should(times(1)).findSupportByIdOrThrow(any());
            assertThat(support.isSecret()).isTrue();
        }

        @Test
        @DisplayName("다른 사람이 상품 문의를 수정하려고 하면 예외을 던진다.")
        void updateProductSupport_Fail_ByNotMatchAuthor() {
            // given
            ProductSupport support = SUPPORT_FIXTURE.toEntity();
            given(productQuery.findSupportByIdOrThrow(any())).willReturn(support);

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> productCommand.updateSupport(
                            SECRET_SUPPORT_FIXTURE.getUserId(),
                            support.getId(),
                            fixture.toDomain())
                    );
        }
    }

    @Nested
    @DisplayName("상품 찜 테스트")
    class favoriteTest {
        private Long userId = 1L;;
        private Favorite favorite;
        private Product product = LA_GOGI.toEntity();

        @BeforeEach
        void initFavorite() {
            favorite = new Favorite(userId, product);
        }

        @Test
        @DisplayName("이미 생성된 찜이 있다면 해당 찜을 활성화 시킨다.")
        void favoriteProduct_Success_WithSavedFavorite() {
            // given
            favorite.softDelete();

            given(favoriteRepository.findByUserIdAndProductId(any(), any())).willReturn(Optional.of(favorite));

            // when
            productCommand.favoriteProduct(userId, product.getId());

            // then
            then(favoriteRepository).should(times(1)).findByUserIdAndProductId(any(), any());
            then(favoriteRepository).should(times(0)).save(any());
            assertThat(favorite.isDeleted()).isFalse();
        }

        @Test
        @DisplayName("저장된 찜이 없다면 새로운 찜을 만들어낸다.")
        void favoriteProduct_Success_WithNewFavorite() {
            // given
            given(favoriteRepository.findByUserIdAndProductId(any(), any())).willReturn(Optional.empty());
            given(favoriteRepository.save(any())).willReturn(favorite);

            // when
            productCommand.favoriteProduct(userId, product.getId());

            // then
            then(favoriteRepository).should(times(1)).findByUserIdAndProductId(any(), any());
            then(favoriteRepository).should(times(1)).save(any());
        }

        @Test
        @DisplayName("삭제 요청이 들어오면 찜의 상태가 DELETED로 변경된다.")
        void cancelFavorite_Success() {
            // given
            given(favoriteRepository.findByUserIdAndProductId(any(), any())).willReturn(Optional.of(favorite));

            // when
            productCommand.cancelFavorite(userId, product.getId());

            // then
            then(favoriteRepository).should(times(1)).findByUserIdAndProductId(any(), any());
            assertThat(favorite.isDeleted()).isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 찜을 취소하려고 하면 예외가 발생한다.")
        void cancelFavorite_Fail_ByNotExistFavorite() {
            // given
            given(favoriteRepository.findByUserIdAndProductId(any(), any())).willReturn(Optional.empty());

            // when, then
            assertThatExceptionOfType(KurlyBaseException.class)
                    .isThrownBy(() -> productCommand.cancelFavorite(userId, product.getId()))
                    .withMessage(NEVER_FAVORITE.getMessage());
        }
    }
}
