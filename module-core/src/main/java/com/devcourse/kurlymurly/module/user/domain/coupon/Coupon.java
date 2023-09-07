package com.devcourse.kurlymurly.module.user.domain.coupon;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "coupons")
public class Coupon extends BaseEntity {
    public enum CouponStatus { NORMAL,EXPIRED,USED }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int Amount;

    @Transient
    private String discountStrategy;

    @Column(nullable = false, length = 10)
    private String status;
    // normal , expired , used
}
