package io.github.joblo2213.JMacros.api;

import io.github.joblo2213.JMacros.api.configuration.Configurable;
import org.pf4j.ExtensionPoint;

public interface Action extends Configurable, ExtensionPoint {

    void run(API api) throws InterruptedException;
}
