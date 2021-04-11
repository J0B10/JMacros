package de.ungefroren.JMacros.api.configuration.parameters;


import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class IntParameter extends Parameter<Integer> {

    public IntParameter(String id, String label, String description) {
        super(id, label, description);
    }

    public IntParameter(String id, String label, String description, int defaultVal) {
        super(id, label, description, defaultVal);
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(getValue());
    }

    @Override
    public void deserialize(JsonElement json) throws Exception {
        setValue(json.getAsInt());
    }
}
