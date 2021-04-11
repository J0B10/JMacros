package io.github.joblo2213.JMacros.api.configuration.parameters;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class EnumParameter<E extends Enum<E>> extends Parameter<E>{

    private final Class<E> enumClass;

    public EnumParameter(String id, String label, String description, Class<E> enumClass) {
        super(id, label, description);
        this.enumClass = enumClass;
    }

    public EnumParameter(String id, String label, String description, E defaultVal) {
        super(id, label, description, defaultVal);
        this.enumClass = defaultVal.getDeclaringClass();
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(getValue().name());
    }

    @Override
    public void deserialize(JsonElement json) throws Exception {
        setValue(Enum.valueOf(enumClass, json.getAsString()));
    }
}
