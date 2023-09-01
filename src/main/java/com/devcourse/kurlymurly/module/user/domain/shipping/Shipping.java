package com.devcourse.kurlymurly.module.user.domain.shipping;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.regex.Pattern;

@Entity
@Table(name = "shippings")
public class Shipping extends BaseEntity {
    private static final Pattern EXPRESS_REGEX = Pattern.compile("^[서울|경기|인천|충청|대구|부산|울산|양산|창원|김해].*");

    public Shipping(Long userId, String roadAddress, boolean isDefault) {
        Address address = checkExpress(roadAddress);

        this.userId = userId;
        this.address = address;
        this.isDefault = isDefault;
    }

    private static Address checkExpress(String roadAddress) {
        boolean matches = EXPRESS_REGEX.matcher(roadAddress).matches();
        Address address = new Address(roadAddress, matches);
        return address;
    }

    protected Shipping() {
    }

    @Column(nullable = false)
    private Long userId;

    @Embedded
    private Address address;

    @Embedded
    private Info info;

    @Column(nullable = false)
    private boolean isDefault;

    public Address getAddress() {
        return address;
    }
}
