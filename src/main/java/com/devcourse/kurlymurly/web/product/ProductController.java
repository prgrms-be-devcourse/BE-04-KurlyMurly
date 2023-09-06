package com.devcourse.kurlymurly.web.product;

import com.devcourse.kurlymurly.module.product.service.ProductFacade;
import com.devcourse.kurlymurly.module.user.domain.User;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.ListPagingResponse;
import com.devcourse.kurlymurly.web.dto.product.favorite.GetFavorite;
import com.devcourse.kurlymurly.web.dto.product.support.SupportProduct;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;
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
    @Operation(description = "[토큰 필요] 유저가 찜한 목록을 보여준다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 찜 목록을 불러왔습니다.")
    @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    @GetMapping("/favorites")
    @ResponseStatus(OK)
    public KurlyResponse<ListPagingResponse> getFavorites(@AuthenticationPrincipal User user) {
        ListPagingResponse<GetFavorite.Response> response = productFacade.getUserFavorites(user.getId());
        return KurlyResponse.ok(response);
    }

    @Tag(name = "product")
    @Operation(description = "[토큰 필요] 상품 문의를 등록한다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품 문의을 작성했습니다.")
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 상품입니다.")
    @PostMapping("/{id}/support")
    @ResponseStatus(OK)
    public KurlyResponse<Void> createProductSupport(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody SupportProduct.Request request
    ) {
        productFacade.createProductSupport(user.getId(), id, request);
        return KurlyResponse.noData();
    }

    @Tag(name = "product")
    @Operation(description = "[토큰 필요] 상품을 찜 목록에 등록한다.")
    @ApiResponse(responseCode = "204", description = "성공적으로 찜 목록에 등록했습니다.")
    @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    @PostMapping("/{id}/favorite")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> favoriteProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        productFacade.favoriteProduct(user.getId(), id);
        return KurlyResponse.noData();
    }

    @Tag(name = "product")
    @Operation(description = "[토큰 필요] 상품 문의를 수정한다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품 문의를 수정했습니다.")
    @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 상품 문의입니다.")
    @ApiResponse(responseCode = "409", description = "작성자가 아닙니다.")
    @PutMapping("/supports/{supportId}")
    @ResponseStatus(OK)
    public KurlyResponse<Void> updateProductSupport(
            @AuthenticationPrincipal User user,
            @PathVariable Long supportId,
            @RequestBody @Valid SupportProduct.Request request
    ) {
        productFacade.updateProductSupport(user.getId(), supportId, request);
        return KurlyResponse.noData();
    }

    @Tag(name = "product")
    @Operation(description = "[토큰 필요] 상품 찜하기를 취소한다.")
    @ApiResponse(responseCode = "204", description = "성공적으로 찜하기를 취소했습니다.")
    @ApiResponse(responseCode = "401", description = "토큰을 넣지 않은 경우")
    @ApiResponse(responseCode = "404", description = "좋아요를 한 적이 없습니다.")
    @DeleteMapping("/{id}/favorite")
    @ResponseStatus(NO_CONTENT)
    public KurlyResponse<Void> cancelFavorite(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        productFacade.cancelFavorite(user.getId(), id);
        return KurlyResponse.noData();
    }
}
