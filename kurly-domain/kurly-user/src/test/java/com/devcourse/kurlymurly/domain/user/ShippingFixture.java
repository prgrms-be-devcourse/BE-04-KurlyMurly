package com.devcourse.kurlymurly.domain.user;

import com.devcourse.kurlymurly.domain.user.shipping.Shipping;

public enum ShippingFixture {
    SHIPPING_FIXTURE(1L, "컬리단길", true);

    private final Long userId;
    private final String address;
    private final boolean isDefault;

    ShippingFixture(Long userId, String address, boolean isDefault) {
        this.userId = userId;
        this.address = address;
        this.isDefault = isDefault;
    }

    public Shipping toEntity() {
        return new Shipping(userId,address,isDefault);
    }
}
