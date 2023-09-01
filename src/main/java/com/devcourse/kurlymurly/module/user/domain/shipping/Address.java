package com.devcourse.kurlymurly.module.user.domain.shipping;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    public Address(String address, boolean matches) {
        this.roadAddress = address;
        this.isExpress = matches;
    }

    protected Address() {
    }

    @Column(nullable = false, length = 100)
    private String roadAddress;

    @Column(nullable = false)
    private boolean isExpress;

    public boolean isExpress() {
        return isExpress;
    }
}
