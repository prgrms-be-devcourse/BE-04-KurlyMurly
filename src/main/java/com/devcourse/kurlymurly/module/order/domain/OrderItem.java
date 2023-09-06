package com.devcourse.kurlymurly.module.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItem {
    @Column(nullable = false)
    private Long productId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int quantity;

    protected OrderItem() {
    }

    public OrderItem(Long productId, String name, int totalPrice, int quantity) {
        this.productId = productId;
        this.name = name;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }
}
