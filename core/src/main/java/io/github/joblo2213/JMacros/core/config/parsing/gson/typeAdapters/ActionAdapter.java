package io.github.joblo2213.JMacros.core.config.parsing.gson.typeAdapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import io.github.joblo2213.JMacros.api.Action;
import io.github.joblo2213.JMacros.api.configuration.parameters.Parameter;
import io.github.joblo2213.JMacros.core.registry.ActionRegistry;

import java.lang.reflect.Type;

public class ActionAdapter implements JsonSerializer<Action>, JsonDeserializer<Action> {

    public static final Type TYPE = TypeToken.get(Action.class).getType();

    @Override
    public Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String className = obj.get("action").getAsString();
        try {
            Action action = ActionRegistry.initialize(className);
            JsonObject parameters = obj.getAsJsonObject("parameters");
            for (Parameter<?> param : action.getParameters()) {
                try {
                    param.deserialize(parameters.get(param.getId()));
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }
            return action;
        } catch (IllegalArgumentException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Action src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("action", ActionRegistry.getName(src));
        JsonObject parameters = new JsonObject();
        src.getParameters().forEach(param -> parameters.add(param.getId(), param.serialize()));
        obj.add("parameters", parameters);
        return obj;
    }
}
