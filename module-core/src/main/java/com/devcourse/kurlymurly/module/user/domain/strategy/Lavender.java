package com.devcourse.kurlymurly.module.user.domain.strategy;

public class Lavender implements Reward {
    private static final double LAVENDER_RATE = 0.05;

    @Override
    public int saveReward(int totalPrice) {
        return (int) (totalPrice * LAVENDER_RATE);
    }
}
