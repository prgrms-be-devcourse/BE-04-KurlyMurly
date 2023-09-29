package com.devcourse.kurlymurly.domain.user.strategy;

public class Lavender implements Reward {
    private static final double LAVENDER_RATE = 0.05;

    @Override
    public int saveReward(int totalPrice) {
        return (int) (totalPrice * LAVENDER_RATE);
    }
}
