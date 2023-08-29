package com.devcourse.kurlymurly.module.product.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    /** 샛별 배송, 일반 배송 */
    public enum Delivery { EXPRESS, NORMAL }

    public enum Status { NORMAL, SOLD_OUT }

    @Column(nullable = false)
    private Long categoryId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Delivery delivery;

    // todo: images

    @Embedded
    private ProductDetail detail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private boolean isKurlyOnly;

    protected Product() {
    }

    public Product(Long categoryId, String name, String description, int price, Delivery delivery,
                   ProductDetail detail, boolean isKurlyOnly) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.delivery = delivery;
        this.detail = detail;
        this.status = Status.NORMAL;
        this.isKurlyOnly = isKurlyOnly;
    }
}
