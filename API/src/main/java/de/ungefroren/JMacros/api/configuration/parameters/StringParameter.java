package de.ungefroren.JMacros.api.configuration.parameters;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonToken;

public class StringParameter extends Parameter<String> {

    public StringParameter(String id, String label, String description) {
        super(id, label, description);
    }

    public StringParameter(String id, String label, String description, String defaultVal) {
        super(id, label, description, defaultVal);
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(getValue());
    }

    @Override
    public void deserialize(JsonElement json) throws Exception {
        setValue(json.getAsString());
    }

}
