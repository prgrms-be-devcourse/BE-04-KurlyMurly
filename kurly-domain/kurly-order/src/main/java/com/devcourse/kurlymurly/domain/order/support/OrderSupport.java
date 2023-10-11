package com.devcourse.kurlymurly.domain.order.support;

import com.devcourse.kurlymurly.common.exception.KurlyBaseException;
import com.devcourse.kurlymurly.data.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.util.Objects;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.NOT_AUTHOR;

@Entity
@Table(name = "order_supports")
public class OrderSupport extends BaseEntity {
    public enum Status {
        PREPARE,
        ANSWERED,
        DELETED
    }

    public enum Type {
        DELIVERY,
        MISSING,
        PRODUCT,
        ORDER,
        EVENT,
        ETC
    }

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String orderNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "text")
    private String answerContent;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    protected OrderSupport() {
    }

    public OrderSupport(Long userId, Long orderId, String orderNumber, String type, String title, String content) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.type = Type.valueOf(type);
        this.title = title;
        this.content = content;
        this.status = Status.PREPARE;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Type getType() {
        return this.type;
    }

    public void toPreparedSupport() {
        this.status = Status.PREPARE;
    }

    public void toAnsweredSupport(String answerContent) {
        this.answerContent = answerContent;
        this.status = Status.ANSWERED;
    }

    public void deleteSupport() {
        this.status = Status.DELETED;
    }

    public void updateOrderSupport(String title, String content) {
        this.title = title;
        this.content = content;
        changeUpdatedDate();
    }

    public void validateAuthor(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw KurlyBaseException.withId(NOT_AUTHOR, userId);
        }
    }
}
