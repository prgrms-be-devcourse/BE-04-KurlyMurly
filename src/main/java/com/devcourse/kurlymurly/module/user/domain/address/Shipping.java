package com.devcourse.kurlymurly.module.user.domain.address;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "shippings")
public class Shipping extends BaseEntity {
    @Column(nullable = false)
    private Long userId;

    @Embedded
    private Address address;

    @Embedded
    private Info info;

    @Column(nullable = false)
    private boolean isDefault;
}
