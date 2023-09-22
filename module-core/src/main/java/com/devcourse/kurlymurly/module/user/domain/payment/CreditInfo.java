package com.devcourse.kurlymurly.module.user.domain.payment;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class CreditInfo {

    public CreditInfo(Date expiredDate, Integer password) {
        this.expiredDate = expiredDate;
        this.password = password;
    }

    public CreditInfo() {
    }

    Date expiredDate;

    Integer password;
}
