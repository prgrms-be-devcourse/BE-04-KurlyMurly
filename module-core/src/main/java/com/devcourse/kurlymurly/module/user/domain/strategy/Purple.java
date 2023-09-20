package com.devcourse.kurlymurly.module.user.domain.strategy;

public class Purple implements Reward {
    private static final double PURPLE_RATE = 0.07;

    @Override
    public int saveReward(int totalPrice) {
        return (int) (totalPrice * PURPLE_RATE);
    }
}
