package de.ungefroren.JMacros.api;

import de.ungefroren.JMacros.api.configuration.Configurable;

public interface Profile extends Configurable {

    String getName();

    int getId();

    boolean isMain();

    Macro[] getMacros();
}
