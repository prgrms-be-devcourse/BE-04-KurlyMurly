package com.devcourse.kurlymurly.module.user.domain.shipping;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Info {
    @Column(length = 30)
    private String receiver;

    @Column(length = 15)
    private String contact;

    @Column(length = 15)
    private String receiveArea;

    @Column(length = 10)
    private String entrancePassword;

    @Column(length = 10)
    private String messageAlertTime;

    public Info() {
        this.receiver = "none";
        this.contact = "none";
        this.receiveArea = "none";
        this.entrancePassword = "none";
        this.messageAlertTime = "none";
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContact() {
        return contact;
    }
}
