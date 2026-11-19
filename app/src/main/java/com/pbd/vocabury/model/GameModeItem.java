package com.pbd.vocabury.model;

public class GameModeItem {

    private final String name;
    private final GameMode mode;

    public GameModeItem(String name, GameMode mode) {

        this.name = name;
        this.mode = mode;

    }

    public GameMode getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return name;
    }

}
