package de.ungefroren.JMacros.api;

import de.ungefroren.JMacros.api.configuration.Configurable;

public interface Action extends Configurable {

    void run() throws InterruptedException;
}
