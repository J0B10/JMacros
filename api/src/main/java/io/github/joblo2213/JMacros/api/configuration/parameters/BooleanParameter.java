package io.github.joblo2213.JMacros.api.configuration.parameters;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanParameter extends Parameter<Boolean> {

    public BooleanParameter(String id, String label, String description) {
        super(id, label, description);
    }

    public BooleanParameter(String id, String label, String description, Boolean defaultVal) {
        super(id, label, description, defaultVal);
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(getValue());
    }

    @Override
    public void deserialize(JsonElement json) throws Exception {
        setValue(json.getAsBoolean());
    }
}
