package com.devcourse.kurlymurly.module.user.domain.shipping;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Info {
    @Column(nullable = false, length = 30)
    private String receiver;

    @Column(nullable = false, length = 15)
    private String contact;

    @Column(nullable = false, length = 15)
    private String receiveArea;

    @Column(nullable = false, length = 10)
    private String entrancePassword;

    @Column(nullable = false, length = 10)
    private String messageAlertTime;

    public Info() {
        this.receiver = "none";
        this.contact = "none";
        this.receiveArea = "none";
        this.entrancePassword = "none";
        this.messageAlertTime = "none";
    }

    public String getReceiver() {
        return this.receiver;
    }

    public String getContact() {
        return this.contact;
    }

    public void update(String receiver, String contact) {
        this.receiver = receiver;
        this.contact = contact;
    }
}
