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
    public enum Status { NORMAL, SOLD_OUT }

    @Column(nullable = false)
    private Long categoryId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Embedded
    private ProductDetail detail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    protected Product() {
    }

    public Product(Long categoryId, String name, String description, int price, ProductDetail detail) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.detail = detail;
        this.status = Status.NORMAL;
    }
}
