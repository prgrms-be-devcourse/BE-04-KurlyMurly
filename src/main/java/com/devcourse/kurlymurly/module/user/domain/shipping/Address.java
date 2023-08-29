package com.devcourse.kurlymurly.module.user.domain.shipping;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(nullable = false, length = 100)
    private String roadAddress;

    @Column(nullable = false, length = 100)
    private String normalAddress;

    @Column(nullable = false)
    private boolean isExpress;
}
