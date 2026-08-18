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

    /**
     * Get whether this input action matches the given criteria, if it does consume it and return true.
     */
    public boolean ifMatchesConsume(int input) {
        if(this.input == input) {
            consume();
            return true;
        }
        return false;
    }
    /**
     * Get whether this input action matches the given criteria, if it does consume it and return true.
     */
    public boolean ifMatchesConsume(int input, int action) {
        if(this.input == input && this.action == action) {
            consume();
            return true;
        }
        return false;
    }

    /**
     * Get whether this input matches the given criteria.
     */
    public boolean matches(int input) {
        return this.input == input;
    }
    /**
     * Get whether this input matches the given criteria.
     */
    public boolean matches(int input, int action) {
        return this.input == input && this.action == action;
    }

    /**
     * Attempt to consume this action if it is not empty.
     * @return Whether this action was consumed.
     */
    public boolean tryConsume() {
        if(input != -1 || action != -1) {
            consume();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "InputAction(" + input + ", " + action + ")";
    }

    /**
     * Consume this input action, resetting it back to {@code InputAction.NONE}
     */
    public void consume() {
        this.input = -1;
        this.action = -1;
    }
}
