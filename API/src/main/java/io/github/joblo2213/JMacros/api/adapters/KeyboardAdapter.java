package io.github.joblo2213.JMacros.api.adapters;

import javafx.scene.input.KeyCode;

public interface KeyboardAdapter {
    void press(KeyCode key);

    void release(KeyCode key);

    void type(KeyCode key);

    void type(KeyCode key, long ms) throws InterruptedException;
}
