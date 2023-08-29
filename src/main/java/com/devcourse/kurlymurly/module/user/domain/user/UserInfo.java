package com.devcourse.kurlymurly.module.user.domain.user;

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
}
