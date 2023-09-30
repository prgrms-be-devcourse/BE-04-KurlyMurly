package com.devcourse.kurlymurly.domain.user.shipping;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(nullable = false, length = 100)
    private String roadAddress;

    @Column(length = 100)
    private String description;

    @Column(nullable = false)
    private boolean isExpress;

    protected Address() {
    }

    public Address(String address, boolean matches) {
        this.roadAddress = address;
        this.isExpress = matches;
    }

    public boolean isExpress() {
        return this.isExpress;
    }

    public String getDescribedAddress() {
        return this.description == null ? this.roadAddress : this.roadAddress + " " + this.description;
    }

    public void update(String description) {
        this.description = description;
    }
}
