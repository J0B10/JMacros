package de.ungefroren.JMacros.api.configuration;

import de.ungefroren.JMacros.api.configuration.parameters.Parameter;

import java.util.Collection;

public interface Configurable {

    Collection<Parameter<?>> getParameters();
}
