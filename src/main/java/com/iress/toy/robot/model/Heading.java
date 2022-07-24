package com.iress.toy.robot.model;

public enum Heading {
    NORTH, EAST, SOUTH, WEST;

    public static Heading get(String command) {
        for (Heading value : values()) {
            if (value.name().equalsIgnoreCase(command)) {
                return value;
            }
        }
        return null;
    }
}
