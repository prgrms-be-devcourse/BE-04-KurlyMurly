package com.devcourse.kurlymurly.module.product.domain.support;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.data.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.util.Objects;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.NOT_AUTHOR;

@Entity
@Table(name = "product_supports")
public class ProductSupport extends BaseEntity {
    public enum Status { NORMAL, ANSWERED, DELETED }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(length = 50, nullable = false)
    private String productName;

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

    public ProductSupport(Long userId, Long productId, String productName, String title, String content, boolean isSecret) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
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
            throw KurlyBaseException.withId(NOT_AUTHOR, userId);
        }
    }

    public boolean isSecret() {
        return isSecret;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Status getStatus() {
        return status;
    }
}
