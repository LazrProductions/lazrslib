package com.lazrproductions.lazrslib.client.screen.base;

public class InputAction {
    public static final InputAction NONE = new InputAction(-1, -1);

    int input;
    int action;

    public InputAction(int input, int action) {
        this.input = input;
        this.action = action;
    }

    public int getInput() {
        return input;
    }
    public int getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "InputAction(" + input + ", " + action + ")";
    }
}