package com.devcourse.kurlymurly.web.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static org.springframework.data.domain.Sort.Direction;

/**
 * 공통적으로 사용하는 페이지 요청 객체
 * @param page 페이지의 인덱스는 0부터 시작하지만 클라이언트에서는 몰라도 되는 부분으로 0을 제외한 양수를 받습니다.
 * @param sortDirection Default로 생성일을 기준으로 내림차순으로 정렬합니다. ASC를 넣어 반대로 정렬할 수 있습니다.
 */
public record KurlyPagingRequest(
        @Positive(message = "페이지 수는 양수만 입력할 수 있습니다.")
        @Schema(description = "페이지 수")
        int page,

        @Schema(requiredMode = NOT_REQUIRED, allowableValues = {"DESC", "ASC"})
        Direction sortDirection
) {
    private static final int INDEX_GAP = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final Direction DEFAULT_DIRECTION = Direction.DESC;
    private static final String SORTING_CRITERIA = "createAt";

    public Pageable toPageable() {
        return PageRequest.of(
                page - INDEX_GAP,
                DEFAULT_SIZE,
                Sort.by(
                        sortDirection == null ? DEFAULT_DIRECTION : sortDirection,
                        SORTING_CRITERIA
                )
        );
    }
}
