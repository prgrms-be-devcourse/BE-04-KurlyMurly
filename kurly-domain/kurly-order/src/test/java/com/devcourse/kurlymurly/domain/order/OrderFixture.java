package com.devcourse.kurlymurly.domain.order;

import com.devcourse.kurlymurly.web.order.CreateOrderLine;

import java.time.LocalDateTime;
import java.util.List;

import static com.devcourse.kurlymurly.domain.order.Order.*;

public enum OrderFixture {
    HEJOW_ORDER(1L, List.of(
            new CreateOrderLine.Request(1L, "소고기", "", 30000, 2),
            new CreateOrderLine.Request(2L, "비싼 소고기", "", 20000, 1)),
            50000,
            0,
            "컬리페이(국민은행)",
            "문희조",
            "010-1234-****",
            "우리집",
            "문 앞",
            "공동현관 비밀번호(****)",
            "종이 포장재"),
    HEJOW_ORDER2(1L, List.of(
            new CreateOrderLine.Request(1L, "소고기", "", 30000, 2),
            new CreateOrderLine.Request(2L, "비싼 소고기", "", 20000, 1)),
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
    private final List<CreateOrderLine.Request> orderItemRequests;
    private final int totalPrice;
    private final int totalDiscount;
    private final String payment;
    private final String receiver;
    private final String phoneNumber;
    private final String address;
    private final String receiveArea;
    private final String entranceInfo;
    private final String packaging;

    OrderFixture(Long userId, List<CreateOrderLine.Request> orderItemRequests, int totalPrice, int totalDiscount, String payment, String receiver, String phoneNumber, String address, String receiveArea, String entranceInfo, String packaging) {
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
                toOrderLines(),
                new PaymentInfo(totalPrice, totalDiscount, payment),
                new ShippingInfo(receiver, phoneNumber, address, receiveArea, entranceInfo, packaging)
        );
    }

    public Order toSpecificStateEntity(Order.Status status) {
        return new Order(
                userId,
                toOrderLines(),
                new PaymentInfo(totalPrice, totalDiscount, payment),
                new ShippingInfo(receiver, phoneNumber, address, receiveArea, entranceInfo, packaging),
                status == Status.DELIVERED ? LocalDateTime.now().minusWeeks(1) : null,
                status,
                status.generateState()
        );
    }

    private List<OrderLine> toOrderLines() {
        return orderItemRequests.stream()
                .map(request -> new OrderLine(
                        request.productId(),
                        request.productName(),
                        request.imageUrl(),
                        request.totalPrice(),
                        request.quantity()
                )).toList();
    }
}
