package com.devcourse.kurlymurly.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ProductDetail {
    /** 냉동, 냉장, 상온 */
    public enum StorageType { FROZEN, REFRIGERATE, ROOM_TEMPERATURE }

    @Column(length = 20, nullable = false)
    private String seller;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorageType storageType;

    @Column(length = 20, nullable = false)
    private String saleUnit;

    @Column(length = 20, nullable = false)
    private String weight;

    @Column(length = 50, nullable = false)
    private String origin;

    @Column(nullable = false)
    private String allergyInfo;

    @Column(length = 50, nullable = false)
    private String expirationInformation;

    protected ProductDetail() {
    }

    public ProductDetail(String seller, StorageType storageType, String saleUnit, String weight,
                         String origin, String allergyInfo, String expirationInformation) {
        this.seller = seller;
        this.storageType = storageType;
        this.saleUnit = saleUnit;
        this.weight = weight;
        this.origin = origin;
        this.allergyInfo = allergyInfo;
        this.expirationInformation = expirationInformation;
    }
}
