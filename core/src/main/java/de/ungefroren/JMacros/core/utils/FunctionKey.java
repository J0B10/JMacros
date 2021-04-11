package de.ungefroren.JMacros.core.utils;

import javafx.scene.input.KeyCode;

import java.util.List;

public abstract class FunctionKey {
    public static final KeyCode F1 = KeyCode.F1;
    public static final KeyCode F2 = KeyCode.F2;
    public static final KeyCode F3 = KeyCode.F3;
    public static final KeyCode F4 = KeyCode.F4;
    public static final KeyCode F5 = KeyCode.F5;
    public static final KeyCode F6 = KeyCode.F6;
    public static final KeyCode F7 = KeyCode.F7;
    public static final KeyCode F8 = KeyCode.F8;
    public static final KeyCode F9 = KeyCode.F9;
    public static final KeyCode F10 = KeyCode.F10;
    public static final KeyCode F11 = KeyCode.F11;
    public static final KeyCode F12 = KeyCode.F12;

    public static final List<KeyCode> VALUES = List.of(F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12);

    public static KeyCode f(int value) throws IndexOutOfBoundsException {
        return VALUES.get(value - 1);
    }

    public static boolean isFunctionKey(KeyCode code) {
        return VALUES.contains(code);
    }
}
