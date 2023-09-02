package com.devcourse.kurlymurly.module.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Formatter;

@Embeddable
public class UserInfo {
    protected UserInfo() {
    }

    public UserInfo(Date birth, String recommender, String sex) {
        this.birth = birth;
        this.recommender = recommender;
        this.sex = sex;
    }

    @Column(nullable = false)
    private Date birth;

    @Column(length = 50)
    private String recommender;

    @Column(nullable = false, length = 10)
    private String sex;

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
