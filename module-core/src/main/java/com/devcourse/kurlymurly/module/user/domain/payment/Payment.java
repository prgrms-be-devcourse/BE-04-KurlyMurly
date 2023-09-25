package com.devcourse.kurlymurly.module.user.domain.payment;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    public enum Type { CREDIT, EASY }

    public enum Status { DEFAULT, NORMAL, EXPIRED, DELETED }

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

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;

    protected Payment() {
    }

    public Payment(Long userId, String payInfo, CreditInfo creditInfo) {
        this.userId = userId;
        this.payInfo = payInfo;
        this.creditInfo = creditInfo;
        this.type = Payment.Type.CREDIT;
        this.status = Status.NORMAL;
    }

    public Payment(Long userId, String payInfo) {
        this.userId = userId;
        this.payInfo = payInfo;
        this.type = Payment.Type.EASY;
        this.status = Status.NORMAL;
        this.creditInfo = new CreditInfo();
    }

    public void deletePayment() {
        this.status = Status.DELETED;
    }
}
