package com.iress.toy.robot.model;

public class Position {
    private int x;
    private int y;
    private Heading heading;

    public Position(int x, int y, Heading heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public Position(Position position) {
        this.x = position.getX();
        this.y = position.getY();
        this.heading = position.getHeading();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    @Override
    public String toString() {
        return getX() + "," + getY() + "," + getHeading().name();
    }
}
