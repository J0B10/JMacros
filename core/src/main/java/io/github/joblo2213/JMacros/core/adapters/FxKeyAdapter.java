package io.github.joblo2213.JMacros.core.adapters;

import io.github.joblo2213.JMacros.api.adapters.KeyboardAdapter;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

import java.util.Objects;

public class FxKeyAdapter implements KeyboardAdapter {

    private final Robot robot;

    public FxKeyAdapter(Robot robot) {
        Objects.requireNonNull(robot);
        this.robot = robot;
    }

    @Override
    public void press(KeyCode key) {
        Objects.requireNonNull(key);
        Platform.runLater(() -> robot.keyPress(key));
    }

    @Override
    public void release(KeyCode key) {
        Objects.requireNonNull(key);
        Platform.runLater(() -> robot.keyRelease(key));
    }

    @Override
    public void type(KeyCode key) {
        Objects.requireNonNull(key);
        Platform.runLater(() -> robot.keyType(key));
    }

    @Override
    public void type(KeyCode key, long ms) throws InterruptedException {
        Objects.requireNonNull(key);
        Platform.runLater(() -> robot.keyPress(key));
        Thread.sleep(ms);
        Platform.runLater(() -> robot.keyRelease(key));
    }
}
