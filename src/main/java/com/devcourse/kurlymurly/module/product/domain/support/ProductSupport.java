package com.devcourse.kurlymurly.module.product.domain.support;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "product_supports")
public class ProductSupport extends BaseEntity {
    public enum Status { NORMAL, ANSWERED, DELETED }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isSecret;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    protected ProductSupport() {
    }

    public ProductSupport(Long userId, Long productId, String title, String content, boolean isSecret) {
        this.userId = userId;
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
        this.status = Status.NORMAL;
    }

    public void update(String title, String content, boolean isSecret) {
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public void validateAuthor(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw new IllegalArgumentException("작성자가 아닙니다. ID : " + userId);
        }
    }

    public boolean isSecret() {
        return isSecret;
    }
}