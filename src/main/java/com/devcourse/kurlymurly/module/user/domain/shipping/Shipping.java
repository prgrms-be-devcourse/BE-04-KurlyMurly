package com.devcourse.kurlymurly.module.user.domain.shipping;

import com.devcourse.kurlymurly.module.BaseEntity;
import com.devcourse.kurlymurly.web.dto.user.shipping.GetAddress;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.regex.Pattern;

@Entity
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

    private Address checkExpress(String roadAddress) {
        boolean matches = EXPRESS_REGEX.matcher(roadAddress).matches();
        return new Address(roadAddress, matches);
    }

    public Address getAddress() {
        return this.address;
    }

    public GetAddress.Response getAddressDto() {
        return new GetAddress.Response(
                this.isDefault,
                this.address.isExpress(),
                this.address.getDescribedAddress(),
                this.info.getReceiver(),
                this.info.getContact()
        );
    }

    public void update(String description, String receiver, String contact) {
        this.address.update(description);
        this.info.update(receiver, contact);
    }
}
