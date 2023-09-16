package com.devcourse.kurlymurly.module.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ShippingInfo {
    @Column(length = 10, nullable = false)
    private String receiver;

    @Column(length = 15, nullable = false)
    private String phoneNumber;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 15, nullable = false)
    private String receiveArea;

    @Column(length = 30, nullable = false)
    private String entranceInfo;

    @Column(length = 10, nullable = false)
    private String packaging;

    protected ShippingInfo() {
    }

    public ShippingInfo(String receiver, String phoneNumber, String address, String receiveArea,
                        String entranceInfo, String packaging) {
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.receiveArea = receiveArea;
        this.entranceInfo = entranceInfo;
        this.packaging = packaging;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getReceiveArea() {
        return receiveArea;
    }

    public String getEntranceInfo() {
        return entranceInfo;
    }

    public String getPackaging() {
        return packaging;
    }
}
