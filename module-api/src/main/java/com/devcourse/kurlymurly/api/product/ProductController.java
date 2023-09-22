package com.devcourse.kurlymurly.api.product;

import com.devcourse.kurlymurly.web.common.KurlyPagingRequest;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.product.FavoriteResponse;
import com.devcourse.kurlymurly.web.product.ProductResponse;
import com.devcourse.kurlymurly.web.product.ReviewRequest;
import com.devcourse.kurlymurly.web.product.ReviewResponse;
import com.devcourse.kurlymurly.web.product.SupportRequest;
import com.devcourse.kurlymurly.application.product.ProductFacade;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.dto.ListPagingResponse;
import com.devcourse.kurlymurly.web.dto.product.favorite.GetFavorite;
import com.devcourse.kurlymurly.web.dto.product.review.CreateReview;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewRequest;
import com.devcourse.kurlymurly.web.dto.product.review.ReviewResponse;
import com.devcourse.kurlymurly.web.dto.product.support.SupportProduct;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "product", description = "상품 API")
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductFacade productFacade;

    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @Tag(name = "product")
    @Operation(summary = "유저 찜목록 가져오기", description = "[토큰 필요] 유저가 찜한 목록을 보여준다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 찜 목록을 불러왔습니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아 발생하는 에러")
    })
    @GetMapping("/favorites") // GET /users/favorites
    @ResponseStatus(OK)
    public KurlyResponse<List<FavoriteResponse.Get>> getFavorites(@AuthenticationPrincipal User user) {
        List<FavoriteResponse.Get> get = productFacade.getUserFavorites(user.getId());
        return KurlyResponse.ok(get);
    }

    @Tag(name = "product")
    @Operation(summary = "카테고리 상품 페이지 가져오기", description = "카테고리에 맞는 상품 목록을 페이징으로 보여준다.")
    @GetMapping("/{categoryId}")
    @ResponseStatus(OK)
    public KurlyResponse<Page<ProductResponse.GetSimple>> getProductPagingOfCategory(
            @PathVariable Long categoryId,
            @ModelAttribute KurlyPagingRequest request
    ) {
        Page<ProductResponse.GetSimple> responses = productFacade.loadProductPageResponse(categoryId, request.toPageable());
        return KurlyResponse.ok(responses);
    }

    @Tag(name = "review")
    @Operation(summary = "상품 리뷰 가져오기", description = "해당 상품에 대한 리뷰 조회 API", responses = {
            @ApiResponse(responseCode = "200", description = "[페이징 정보] 성공적으로 상품의 후기를 가져온 상태"),
    })
    @GetMapping("/{productId}/reviews")
    @ResponseStatus(OK)
    public KurlyResponse<Slice<ReviewResponse.OfProduct>> getReviewsOfProduct(
            @PathVariable Long productId,
            @RequestBody ReviewRequest.OfProduct request
    ) {
        Slice<ReviewResponse.OfProduct> responses = productFacade.loadReviewsOfProduct(productId, request);
        return KurlyResponse.ok(responses);
    }

    @Tag(name = "product")
    @Operation(summary = "상품 문의 등록하기", description = "[토큰 필요] 상품 문의를 등록한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 상품 문의을 작성했습니다."),
            @ApiResponse(responseCode = "400", description = "삭제된 상품에 대한 문의를 등록할 때 발생하는 에러"),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아 발생하는 에러"),
            @ApiResponse(responseCode = "404", description = "요청에 맞는 문의를 찾을 수 없어서 발생하는 에러")
    })
    @PostMapping("/{id}/support")
    @ResponseStatus(OK)
    public KurlyResponse<Void> createProductSupport(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody SupportRequest.Create request
    ) {
        productFacade.createProductSupport(user.getId(), id, request);
        return KurlyResponse.noData();
    }

    @Tag(name = "product")
    @Operation(summary = "상품 찜하기", description = "[토큰 필요] 상품을 찜 목록에 등록한다.", responses = {
            @ApiResponse(responseCode = "204", description = "성공적으로 찜 목록에 등록했습니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아 발생하는 에러")
    })
    @PostMapping("/{id}/favorite")
    @ResponseStatus(OK)
    public KurlyResponse<Void> favoriteProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        productFacade.favoriteProduct(user.getId(), id);
        return KurlyResponse.noData();
    }

    @Tag(name = "product")
    @Operation(summary = "상품 문의 수정하기", description = "[토큰 필요] 상품 문의를 수정한다. 비밀글로 변경하기도 처리한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 상품 문의를 수정했습니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아 발생하는 에러"),
            @ApiResponse(responseCode = "404", description = "요청에 맞는 문의를 찾을 수 없어서 발생하는 에러"),
            @ApiResponse(responseCode = "409", description = "다른 유저가 수정을 시도해서 발생하는 에러")
    })
    @PutMapping("/supports/{supportId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateProductSupport(
            @AuthenticationPrincipal User user,
            @PathVariable Long supportId,
            @RequestBody SupportRequest.Update request
    ) {
        productFacade.updateProductSupport(user.getId(), supportId, request);
        return KurlyResponse.noData();
    }

    @Tag(name = "product")
    @Operation(summary = "상품 찜 취소하기", description = "[토큰 필요] 상품 찜하기를 취소한다.", responses = {
            @ApiResponse(responseCode = "204", description = "성공적으로 찜하기를 취소했습니다."),
            @ApiResponse(responseCode = "401", description = "토큰을 넣지 않아 발생하는 에러"),
            @ApiResponse(responseCode = "404", description = "찜한 적 없는 상품을 취소하려고 시도해서 발생하는 에러")
    })
    @DeleteMapping("/{id}/favorite")
    @ResponseStatus(OK)
    public KurlyResponse<Void> cancelFavorite(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        productFacade.cancelFavorite(user.getId(), id);
        return KurlyResponse.noData();
    }
}
