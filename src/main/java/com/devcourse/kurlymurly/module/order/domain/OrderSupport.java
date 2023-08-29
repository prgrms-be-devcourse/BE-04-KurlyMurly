package com.devcourse.kurlymurly.module.order.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_supports")
public class OrderSupport extends BaseEntity {

    public enum Status {
        PREPARE,
        START,
        DONE
    }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    protected OrderSupport() {
    }

    public OrderSupport(Long userId, Long orderId, String title, String content) {
        this.userId = userId;
        this.orderId = orderId;
        this.title = title;
        this.content = content;
        this.status = Status.PREPARE;
    }
}
