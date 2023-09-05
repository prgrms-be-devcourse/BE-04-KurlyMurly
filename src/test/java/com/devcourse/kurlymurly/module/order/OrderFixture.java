package com.devcourse.kurlymurly.module.order;

import com.devcourse.kurlymurly.module.order.domain.Order;
import com.devcourse.kurlymurly.module.order.domain.OrderItem;
import com.devcourse.kurlymurly.module.order.domain.PaymentInfo;
import com.devcourse.kurlymurly.module.order.domain.ShippingInfo;
import com.devcourse.kurlymurly.web.dto.order.CreateOrder;
import com.devcourse.kurlymurly.web.dto.order.CreateOrderItem;

import java.util.List;

public enum OrderFixture {
    HEJOW_ORDER(1L, List.of(
            new CreateOrderItem.Request(1L, "소고기", 30000, 2),
            new CreateOrderItem.Request(2L, "비싼 소고기", 20000, 1)),
            50000,
            0,
            "컬리페이(국민은행)",
            "문희조",
            "010-1234-****",
            "우리집",
            "문 앞",
            "공동현관 비밀번호(****)",
            "종이 포장재");

    private final Long userId;
    private final List<CreateOrderItem.Request> orderItemRequests;
    private final int totalPrice;
    private final int totalDiscount;
    private final String payment;
    private final String receiver;
    private final String phoneNumber;
    private final String address;
    private final String receiveArea;
    private final String entranceInfo;
    private final String packaging;

    OrderFixture(Long userId, List<CreateOrderItem.Request> orderItemRequests, int totalPrice, int totalDiscount, String payment, String receiver, String phoneNumber, String address, String receiveArea, String entranceInfo, String packaging) {
        this.userId = userId;
        this.orderItemRequests = orderItemRequests;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.payment = payment;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.receiveArea = receiveArea;
        this.entranceInfo = entranceInfo;
        this.packaging = packaging;
    }

    public Order toEntity() {
        return new Order(
                userId,
                toOrderItem(),
                new PaymentInfo(totalPrice, totalDiscount, payment),
                new ShippingInfo(receiver, phoneNumber, address, receiveArea, entranceInfo, packaging)
        );
    }

    public CreateOrder.Request toRequest() {
        return new CreateOrder.Request(
                orderItemRequests,
                totalPrice,
                totalDiscount,
                payment,
                receiver,
                phoneNumber,
                address,
                receiveArea,
                entranceInfo,
                packaging
        );
    }

    private List<OrderItem> toOrderItem() {
        return orderItemRequests.stream()
                .map(request -> new OrderItem(request.productId(), request.name(), request.totalPrice(), request.quantity()))
                .toList();
    }

    public Long getUserId() {
        return userId;
    }

    public List<CreateOrderItem.Request> getOrderItemRequests() {
        return orderItemRequests;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public String getPayment() {
        return payment;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getReceiveArea() {
        return receiveArea;
    }

    public String getEntranceInfo() {
        return entranceInfo;
    }

    public String getPackaging() {
        return packaging;
    }
}
