package com.devcourse.kurlymurly.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class OrderLine {
    @Column(nullable = false)
    private Long productId;

    @Column(length = 50, nullable = false)
    private String productName;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean isReviewed;

    protected OrderLine() {
    }

    public OrderLine(Long productId, String productName, String imageUrl, int totalPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.isReviewed = false;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void reviewed() {
        this.isReviewed = true;
    }

    public boolean isNotReviewed() {
        return !this.isReviewed;
    }
}
