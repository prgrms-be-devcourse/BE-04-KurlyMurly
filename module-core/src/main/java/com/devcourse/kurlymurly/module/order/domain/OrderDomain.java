package com.devcourse.kurlymurly.module.order.domain;

import java.util.ArrayList;
import java.util.List;

public class OrderDomain {
    private List<OrderItem> orderItems = new ArrayList<>();

    private int totalPrice;

    private int totalDiscount;

    private String payment;

    private String receiver;

    private String phoneNumber;

    private String address;

    private String receiveArea;

    private String entranceInfo;

    private String packaging;

    private OrderState state;

    private Order.Status orderStatus;


    public OrderDomain(List<OrderItem> orderItems,
                       int totalPrice,
                       int totalDiscount,
                       String payment,
                       String receiver,
                       String phoneNumber,
                       String address,
                       String receiveArea,
                       String entranceInfo,
                       String packaging) {
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.payment = payment;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.receiveArea = receiveArea;
        this.entranceInfo = entranceInfo;
        this.packaging = packaging;
        this.state = new Ordered();
        orderStatus = Order.Status.ORDERED;
    }

    public void toNextState() {
        this.state = state.nextState();
    }

    public void toCancel() {
        this.state = state.cancel();
        orderStatus = Order.Status.CANCELED;
    }

    public Order toEntity(Long userId) {
        return new Order(
                userId,
                orderItems,
                new PaymentInfo(
                        totalPrice,
                        totalDiscount,
                        payment
                ),
                new ShippingInfo(
                        receiver,
                        phoneNumber,
                        address,
                        receiveArea,
                        entranceInfo,
                        packaging
                )
        );
    }
}