package com.devcourse.kurlymurly.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class UserInfo {
    @Column(nullable = false)
    private LocalDate birth;

    @Column(length = 50)
    private String recommender;

    @Column(nullable = false, length = 10)
    private String sex;

    protected UserInfo() {
    }

    public UserInfo(LocalDate birth, String recommender, String sex) {
        this.birth = birth;
        this.recommender = recommender;
        this.sex = sex;
    }

    public void update(LocalDate birth, String sex) {
        this.birth = birth;
        this.sex = sex;
    }
}
