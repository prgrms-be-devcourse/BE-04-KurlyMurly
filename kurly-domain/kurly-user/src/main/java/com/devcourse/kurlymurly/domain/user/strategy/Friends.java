package com.devcourse.kurlymurly.domain.user.strategy;

public class Friends implements Reward {
    private static final double FRIENDS_RATE = 0.01;

    @Override
    public int saveReward(int totalPrice) {
        return (int) (totalPrice * FRIENDS_RATE);
    }
}
