package io.github.joblo2213.JMacros.api.configuration.parameters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javafx.scene.paint.Color;

public class ColorParameter extends Parameter<Color> {

    public ColorParameter(String id, String label, String description) {
        super(id, label, description);
    }

    public ColorParameter(String id, String label, String description, Color defaultVal) {
        super(id, label, description, defaultVal);
    }

    @Override
    public JsonElement serialize() {
        JsonObject obj = new JsonObject();
        obj.add("red", new JsonPrimitive(getValue().getRed()));
        obj.add("green", new JsonPrimitive(getValue().getGreen()));
        obj.add("blue", new JsonPrimitive(getValue().getBlue()));
        obj.add("opacity", new JsonPrimitive(getValue().getOpacity()));
        return obj;
    }

    @Override
    public void deserialize(JsonElement json) throws Exception {
        JsonObject obj = json.getAsJsonObject();
        Color c = new Color(
                obj.get("red").getAsDouble(),
                obj.get("green").getAsDouble(),
                obj.get("blue").getAsDouble(),
                obj.get("opacity").getAsDouble()
        );
        setValue(c);
    }
}
