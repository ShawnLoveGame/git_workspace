package com.ai.smart.third.enums;

public enum RedisTimeEnum {

    ONE_MINUTES("ONE_MINUTES",60);

    private String name;

    private int second;

    RedisTimeEnum(String name, int second) {
        this.name = name;
        this.second = second;
    }

    public String getName() {
        return name;
    }


    public int getSecond() {
        return second;
    }
}
