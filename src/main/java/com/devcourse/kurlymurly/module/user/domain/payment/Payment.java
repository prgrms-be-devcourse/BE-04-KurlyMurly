package com.devcourse.kurlymurly.module.user.domain.payment;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    public enum Type { CREDIT, EASY;}

    public enum PaymentStatus { DEFAULT,NORMAL,EXPIRED;}
    public Payment(Long userId, String payInfo, CreditInfo creditInfo, Type type, PaymentStatus status) {
        this.userId = userId;
        this.payInfo = payInfo;
        this.creditInfo = creditInfo;
        this.type = type;
        this.status = status;
    }

    public Payment(Long userId, String payInfo, Type type, PaymentStatus status) {
        this.userId = userId;
        this.payInfo = payInfo;
        this.type = type;
        this.status = status;
    }

    public Payment() {
    }

    @Column(nullable = false)
    private Long userId;

    /**
     * 결제 수단 정보
     * ex) 계좌 번호 , 카드 번호
     */
    @Column(nullable = false)
    private String payInfo;

    @Embedded
    private CreditInfo creditInfo;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false, length = 10)
    private PaymentStatus status;
}
