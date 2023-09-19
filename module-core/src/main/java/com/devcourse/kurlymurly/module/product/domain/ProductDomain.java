package com.devcourse.kurlymurly.module.product.domain;

public class ProductDomain {
    private final String name;
    private final String description;
    private final int price;
    private final Product.Delivery delivery;
    private final boolean isKurlyOnly;
    private final String seller;
    private final ProductDetail.StorageType storageType;
    private final String saleUnit;
    private final String weight;
    private final String origin;
    private final String allergyInfo;
    private final String expirationInformation;

    public ProductDomain(String name,
                         String description,
                         int price,
                         Product.Delivery delivery,
                         boolean isKurlyOnly,
                         String seller,
                         ProductDetail.StorageType storageType,
                         String saleUnit,
                         String weight,
                         String origin,
                         String allergyInfo,
                         String expirationInformation) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.delivery = delivery;
        this.isKurlyOnly = isKurlyOnly;
        this.seller = seller;
        this.storageType = storageType;
        this.saleUnit = saleUnit;
        this.weight = weight;
        this.origin = origin;
        this.allergyInfo = allergyInfo;
        this.expirationInformation = expirationInformation;
    }

    public Product toEntity(Long categoryId, String imageUrl) {
        return new Product(
                categoryId,
                name,
                description,
                price,
                delivery,
                imageUrl,
                new ProductDetail(seller,
                        storageType,
                        saleUnit,
                        weight,
                        origin,
                        allergyInfo,
                        expirationInformation),
                isKurlyOnly
        );
    }
}
