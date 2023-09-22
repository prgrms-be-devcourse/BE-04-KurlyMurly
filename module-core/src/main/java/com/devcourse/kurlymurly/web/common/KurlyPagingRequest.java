package com.devcourse.kurlymurly.web.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction;

public record KurlyPagingRequest(
        @Positive(message = "페이지 수는 양수만 입력할 수 있습니다.")
        @Schema(description = "페이지 수")
        int page,

        @Schema(allowableValues = {"DESC", "ASC"})
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
