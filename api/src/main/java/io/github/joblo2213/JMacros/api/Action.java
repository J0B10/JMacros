package io.github.joblo2213.JMacros.api;

import io.github.joblo2213.JMacros.api.configuration.Configurable;

public interface Action extends Configurable {

    void run() throws InterruptedException;
}
