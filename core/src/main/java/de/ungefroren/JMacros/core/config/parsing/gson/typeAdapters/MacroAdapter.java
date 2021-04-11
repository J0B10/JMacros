package de.ungefroren.JMacros.core.config.parsing.gson.typeAdapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import de.ungefroren.JMacros.api.configuration.parameters.Parameter;
import de.ungefroren.JMacros.core.config.MacroData;

import java.lang.reflect.Type;

public class MacroAdapter implements JsonSerializer<MacroData>, JsonDeserializer<MacroData> {

    public static final Type TYPE = TypeToken.get(MacroData.class).getType();

    @Override
    public JsonElement serialize(MacroData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        JsonObject parameters = new JsonObject();
        src.getParameters().forEach(param -> parameters.add(param.getId(), param.serialize()));
        obj.add("parameters", parameters);
        JsonArray actions = new JsonArray(src.getActions().size());
        src.getActions().forEach(action -> actions.add(context.serialize(action)));
        obj.add("actions", actions);
        return obj;
    }

    @Override
    public MacroData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        MacroData m = new MacroData();
        JsonObject parameters = obj.getAsJsonObject("parameters");
        for (Parameter<?> param : m.getParameters()) {
            try {
                param.deserialize(parameters.get(param.getId()));
            } catch (Exception e) {
                throw new JsonParseException(e);
            }
        }
        JsonArray actions = obj.getAsJsonArray("actions");
        actions.forEach(ele -> m.getActions().add(context.deserialize(ele, ActionAdapter.TYPE)));
        return m;
    }
}
