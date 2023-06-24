package com.sm.backend.module.member.domain.entity;

public enum Level {
    ROOKIE1(1), ROOKIE2(2), ROOKIE3(3),
    BEGINNER1(4), BEGINNER2(5), BEGINNER3(6),
    AMATEUR1(7), AMATEUR2(8), AMATEUR3(9),
    SEMIPRO1(10), SEMIPRO2(11), SEMIPRO3(12),
    PRO1(13), PRO2(14), PRO3(15);

    private int levelPoint;

    Level(int levelPoint) {
        this.levelPoint = levelPoint;
    }

    public int getLevelPoint() {
        return levelPoint;
    }
}
