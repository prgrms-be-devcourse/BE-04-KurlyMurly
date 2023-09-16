package com.devcourse.kurlymurly.module.user.domain.shipping;

import com.devcourse.kurlymurly.module.BaseEntity;
import com.devcourse.kurlymurly.web.dto.user.shipping.GetAddress;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import java.util.regex.Pattern;

@Entity
@DynamicUpdate
@Table(name = "shippings")
public class Shipping extends BaseEntity {
    private static final Pattern EXPRESS_REGEX = Pattern.compile("^[서울|경기|인천|충청|대구|부산|울산|양산|창원|김해].*");

    @Column(nullable = false)
    private Long userId;

    @Embedded
    private Address address;

    @Embedded
    private Info info;

    @Column(nullable = false)
    private boolean isDefault;

    protected Shipping() {
    }

    public Shipping(Long userId, String roadAddress, boolean isDefault) {
        Address address = checkExpress(roadAddress);

        this.userId = userId;
        this.address = address;
        this.isDefault = isDefault;
        this.info = new Info();
    }

    public Address getAddress() {
        return this.address;
    }

    public Info getInfo() {
        return info;
    }

    public boolean isDefault() {
        return isDefault;
    }

    private Address checkExpress(String roadAddress) {
        boolean matches = EXPRESS_REGEX.matcher(roadAddress).matches();
        return new Address(roadAddress, matches);
    }

    public void update(String description, String receiver, String contact) {
        this.address.update(description);
        this.info.update(receiver, contact);
    }

    public void updateInfo(String receiver, String contact, String receiveArea,
                           String entrancePassword, String alertTime) {
        this.info.updateInfo(receiver, contact, receiveArea, entrancePassword, alertTime);
    }
}
