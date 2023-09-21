package com.devcourse.kurlymurly.module.user.domain.payment;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class CreditInfo {
    private Date expiredDate;

    private String password;

    protected CreditInfo() {
        this.password = "none";
    }

    public CreditInfo(Date expiredDate, String password) {
        this.expiredDate = expiredDate;
        this.password = password;
    }
}
