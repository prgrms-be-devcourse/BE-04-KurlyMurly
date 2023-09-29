package com.devcourse.kurlymurly.domain.user.strategy;

public class Purple implements Reward {
    private static final double PURPLE_RATE = 0.07;

    @Override
    public int saveReward(int totalPrice) {
        return (int) (totalPrice * PURPLE_RATE);
    }
}
