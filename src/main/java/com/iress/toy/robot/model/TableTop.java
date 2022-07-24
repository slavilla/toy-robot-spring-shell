package com.iress.toy.robot.model;

public class TableTop {
    private final int width;
    private final int height;

    public TableTop(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOutside(int x, int y) {
        return x < 0 || x >= getWidth() ||
                y < 0 || y >= getHeight();
    }
}
