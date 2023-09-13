package com.devcourse.kurlymurly.module.user.domain.shipping;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Info {
    public enum Area { DOOR, OFFICE, LOCKER, ETC }

    public enum AlertTime { ALWAYS, AFTER_7_AM }

    @Column(nullable = false, length = 30)
    private String receiver;

    @Column(nullable = false, length = 15)
    private String contact;

    @Enumerated(value = EnumType.STRING)
    private Area receiveArea;

    @Column(length = 10)
    private String entrancePassword;

    @Enumerated(value = EnumType.STRING)
    private AlertTime messageAlertTime;

    public Info() {
        this.receiver = "none";
        this.contact = "none";
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

    public void updateInfo(
            String receiver,
            String contact,
            String receiveArea,
            String entrancePassword,
            String messageAlertTime
    ) {
        this.receiver = receiver;
        this.contact = contact;
        this.receiveArea = Area.valueOf(receiveArea);
        this.entrancePassword = entrancePassword;
        this.messageAlertTime = AlertTime.valueOf(messageAlertTime);
    }
}
