package com.devcourse.kurlymurly.module.product;

import com.devcourse.kurlymurly.module.product.domain.category.Category;

public enum CategoryFixture {
    KOREAN_SOGOGI("정육/계란", "국내산 소고기"),
    VEGETABLE("채소", "친환경")
    ;

    private final String name;
    private final String subCategory;

    CategoryFixture(String name, String subCategory) {
        this.name = name;
        this.subCategory = subCategory;
    }

    public Category toEntity() {
        return new Category(this.name, this.subCategory);
    }
}
