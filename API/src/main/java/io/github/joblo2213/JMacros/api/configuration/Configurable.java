package io.github.joblo2213.JMacros.api.configuration;

import io.github.joblo2213.JMacros.api.configuration.parameters.Parameter;

import java.util.Collection;

public interface Configurable {

    Collection<Parameter<?>> getParameters();
}
