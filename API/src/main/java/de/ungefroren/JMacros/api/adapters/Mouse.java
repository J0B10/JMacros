package de.ungefroren.JMacros.api.adapters;

public interface Mouse {
    void click(int mouseButton);

    void press(int mouseButton);

    void press(int mouseButton, double ms);

    void release(int mouseButton);

    void moveTo(int x, int y);
}
