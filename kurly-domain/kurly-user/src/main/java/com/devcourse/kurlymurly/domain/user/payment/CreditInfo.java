package com.devcourse.kurlymurly.domain.user.payment;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class CreditInfo {
    private Date expiredDate;
    private String password;

    protected CreditInfo() {
    }

    public CreditInfo(Date expiredDate, String password) {
        this.expiredDate = expiredDate;
        this.password = password;
    }
}
