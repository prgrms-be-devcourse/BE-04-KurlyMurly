package com.devcourse.kurlymurly.module.product;

import com.devcourse.kurlymurly.module.product.domain.Product;
import com.devcourse.kurlymurly.module.product.domain.ProductDetail;
import com.devcourse.kurlymurly.web.dto.product.CreateProduct;

import static com.devcourse.kurlymurly.module.product.domain.Product.Delivery;
import static com.devcourse.kurlymurly.module.product.domain.ProductDetail.StorageType;

public enum ProductFixture {
    LA_GOGI(1L,
            "[고기반찬] 양념 LA 갈비 800g",
            "100g당 가격 4,363원",
            34_900,
            Delivery.EXPRESS,
            "컬리",
            StorageType.FROZEN,
            "1개",
            "800g",
            "상품설명/상세정보 참조",
            "-쇠고기, 대두, 밀 함유",
            "수령일 포함 165일",
            true),
    ;

    private final Long categoryId;
    private final String name;
    private final String description;
    private final int price;
    private final Delivery delivery;
    private final String seller;
    private final StorageType storageType;
    private final String saleUnit;
    private final String weight;
    private final String origin;
    private final String allergyInfo;
    private final String expirationInformation;
    private final boolean isKurlyOnly;

    ProductFixture(Long categoryId, String name, String description, int price, Delivery delivery, String seller, StorageType storageType,
                   String saleUnit, String weight, String origin, String allergyInfo, String expirationInformation, boolean isKurlyOnly) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.delivery = delivery;
        this.seller = seller;
        this.storageType = storageType;
        this.saleUnit = saleUnit;
        this.weight = weight;
        this.origin = origin;
        this.allergyInfo = allergyInfo;
        this.expirationInformation = expirationInformation;
        this.isKurlyOnly = isKurlyOnly;
    }

    public Product toEntity() {
        ProductDetail detail = new ProductDetail(this.seller,
                this.storageType,
                this.saleUnit,
                this.weight,
                this.origin,
                this.allergyInfo,
                this.expirationInformation);

        return new Product(this.categoryId,
                this.name,
                this.description,
                this.price,
                this.delivery,
                detail,
                this.isKurlyOnly);
    }

    public CreateProduct.Request toRequest() {
        return new CreateProduct.Request(
                this.categoryId,
                this.name,
                this.description,
                this.price,
                this.delivery,
                this.seller,
                this.storageType,
                this.saleUnit,
                this.weight,
                this.origin,
                this.allergyInfo,
                this.expirationInformation,
                this.isKurlyOnly
        );
    }
}
