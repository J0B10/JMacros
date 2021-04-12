package io.github.joblo2213.JMacros.api.configuration.parameters;

import com.google.gson.JsonElement;
import io.github.joblo2213.JMacros.api.configuration.InvalidParameterException;

public abstract class Parameter<T> {
    private T value;
    private final String id;
    private final String label;
    private final String description;

    Parameter(String id, String label, String description) {
        this.id = id;
        this.label = label;
        this.description = description;
    }

    Parameter(String id, String label, String description, T defaultVal) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.value = defaultVal;
    }


    public T getValue() {
        return value;
    }

    public void setValue(T value) throws InvalidParameterException {
        validate(value);
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    protected void validate(T value) throws InvalidParameterException { //TODO must be checked if value!=null ?
    }

    public abstract JsonElement serialize();

    public abstract void deserialize(JsonElement json) throws Exception; //TODO More precise handling of invalid configs
}
