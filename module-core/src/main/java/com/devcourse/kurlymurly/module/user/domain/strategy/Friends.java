package com.devcourse.kurlymurly.module.user.domain.strategy;

public class Friends implements Reward {
    private static final double FRIENDS_RATE = 0.01;

    @Override
    public int saveReward(int totalPrice) {
        return (int) (totalPrice * FRIENDS_RATE);
    }
}
