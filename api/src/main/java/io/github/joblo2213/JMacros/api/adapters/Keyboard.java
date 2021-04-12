package io.github.joblo2213.JMacros.api.adapters;

import javafx.scene.input.KeyCode;

public interface Keyboard {
    void press(KeyCode keyCode);

    void press(KeyCode keyCode, double ms);

    void release(KeyCode keyCode);
}
