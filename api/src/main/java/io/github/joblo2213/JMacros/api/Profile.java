package io.github.joblo2213.JMacros.api;

import io.github.joblo2213.JMacros.api.configuration.Configurable;

public interface Profile extends Configurable {

    String getName();

    int getId();

    boolean isMain();

    Macro[] getMacros();
}
