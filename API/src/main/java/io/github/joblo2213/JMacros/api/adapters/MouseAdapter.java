package io.github.joblo2213.JMacros.api.adapters;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;

public interface MouseAdapter {
    void click(MouseButton mouseButton);

    void click(MouseButton mouseButton, long ms) throws InterruptedException;

    void press(MouseButton mouseButton);

    void release(MouseButton mouseButton);

    void moveTo(double x, double y);

    void moveTo(Point2D pos);

    double getCursorX();

    double getCursorY();

    Point2D getCursorPos();
}
