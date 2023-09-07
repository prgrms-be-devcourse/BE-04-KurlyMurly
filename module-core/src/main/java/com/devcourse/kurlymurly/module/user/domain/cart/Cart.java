package com.devcourse.kurlymurly.module.user.domain.cart;

import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static com.devcourse.kurlymurly.global.exception.ErrorCode.NOT_CORRECT_QUANTITY;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    protected Cart() {
    }

    public Cart(Long userId, Long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void updateQuantity(boolean isIncrease) {
        if (isIncrease) {
            this.quantity += 1;
        } else {
            validQuantity();
            this.quantity -= 1;
        }
    }

    private void validQuantity() {
        if(1 >= this.quantity) {
            throw new KurlyBaseException(NOT_CORRECT_QUANTITY);
        }
    }
}
