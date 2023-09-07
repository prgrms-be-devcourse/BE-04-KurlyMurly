package com.devcourse.kurlymurly.module.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class UserInfo {
    @Column(nullable = false)
    private Date birth;

    @Column(length = 50)
    private String recommender;

    @Column(nullable = false, length = 10)
    private String sex;

    protected UserInfo() {
    }

    public UserInfo(Date birth, String recommender, String sex) {
        this.birth = birth;
        this.recommender = recommender;
        this.sex = sex;
    }

    public void update(Date birth, String sex) {
        this.birth = birth;
        this.sex = sex;
    }
}
