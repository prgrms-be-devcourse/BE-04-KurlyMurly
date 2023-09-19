package com.devcourse.kurlymurly.module.user.domain.strategy;

public class White implements Reward {
    private static final double WHITE_RATE = 0.03;

    @Override
    public int saveReward(int totalPrice) {
        return (int) (totalPrice * WHITE_RATE);
    }
}
