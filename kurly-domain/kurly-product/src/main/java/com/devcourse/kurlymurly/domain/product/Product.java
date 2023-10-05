package com.devcourse.kurlymurly.domain.product;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.data.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.DELETED_PRODUCT;
import static com.devcourse.kurlymurly.core.exception.ErrorCode.INORDERABLE_PRODUCT;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    /** 샛별 배송, 일반 배송 */
    public enum Delivery { EXPRESS, NORMAL }

    public enum Status { NORMAL, SOLD_OUT, DELETED, BEST }

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

    @Column(nullable = false)
    private String imageUrl;

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
                   String imageUrl, ProductDetail detail, boolean isKurlyOnly) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.delivery = delivery;
        this.imageUrl = imageUrl;
        this.detail = detail;
        this.status = Status.NORMAL;
        this.isKurlyOnly = isKurlyOnly;
    }

    public void validateOrderable() {
        if (this.status != Status.NORMAL) {
            throw KurlyBaseException.withId(INORDERABLE_PRODUCT, this.getId());
        }
    }

    public void validateSupportable() {
        if (this.status == Status.DELETED) {
            throw KurlyBaseException.withId(DELETED_PRODUCT, this.getId());
        }
    }

    public void softDelete() {
        this.status = Status.DELETED;
    }

    public void soldOut() {
        validateSupportable();
        this.status = Status.SOLD_OUT;
    }

    public void toBest() {
        validateSupportable();
        this.status = Status.BEST;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public int getPrice() {
        return price;
    }
}
