package de.ungefroren.JMacros.api.configuration;

import de.ungefroren.JMacros.api.configuration.parameters.Parameter;

public class InvalidParameterException extends Exception {

    private final Parameter<?> parameter;

    public InvalidParameterException(Parameter<?> parameter) {
        this(parameter, parameter.getLabel() + " parameter is invalid");
    }

    public InvalidParameterException(Parameter<?> parameter, String message) {
        super(message);
        this.parameter = parameter;
    }

    public InvalidParameterException(Parameter<?> parameter, String message, Throwable cause) {
        super(message, cause);
        this.parameter = parameter;
    }

    public InvalidParameterException(Parameter<?> parameter, Throwable cause) {
        super(cause);
        this.parameter = parameter;
    }

    public Parameter<?> getParameter() {
        return parameter;
    }
}
