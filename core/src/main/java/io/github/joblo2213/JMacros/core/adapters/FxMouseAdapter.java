package io.github.joblo2213.JMacros.core.adapters;

import io.github.joblo2213.JMacros.api.adapters.MouseAdapter;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;

import java.util.Objects;

public class FxMouseAdapter implements MouseAdapter {

    private final Robot robot;

    public FxMouseAdapter(Robot robot) {
        Objects.requireNonNull(robot);
        this.robot = robot;
    }

    @Override
    public void click(MouseButton button) {
        Platform.runLater(() -> robot.mouseClick(button));
    }

    @Override
    public void click(MouseButton button, long ms) throws InterruptedException {
        Objects.requireNonNull(button);
        Platform.runLater(() -> robot.mousePress(button));
        Thread.sleep(ms);
        Platform.runLater(() -> robot.mouseRelease(button));
    }

    @Override
    public void press(MouseButton button) {
        Objects.requireNonNull(button);
        Platform.runLater(() -> robot.mousePress(button));
    }

    @Override
    public void release(MouseButton button) {
        Objects.requireNonNull(button);
        Platform.runLater(() -> robot.mouseRelease(button));
    }

    @Override
    public void moveTo(double x, double y) {
        Platform.runLater(() -> robot.mouseMove(x, y));
    }

    @Override
    public void moveTo(Point2D pos) {
        Objects.requireNonNull(pos);
        Platform.runLater(() -> robot.mouseMove(pos));
    }

    //TODO Test if robot queries must be run from application thread...
    //  If this is the case, use completable future to wait for result
    @Override
    public double getCursorX() {
        return robot.getMouseX();
    }

    @Override
    public double getCursorY() {
        return robot.getMouseY();
    }

    @Override
    public Point2D getCursorPos() {
        return robot.getMousePosition();
    }
}
