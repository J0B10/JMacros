package io.github.joblo2213.JMacros.core.config.parsing.gson.typeAdapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.github.joblo2213.JMacros.api.configuration.parameters.Parameter;
import io.github.joblo2213.JMacros.core.config.Config;

import java.lang.reflect.Type;

public class ConfigAdapter implements JsonSerializer<Config>, JsonDeserializer<Config> {

    @Override
    public Config deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Config config = new Config();
        for (Parameter<?> param : config.getParameters()) {
            try {
                param.deserialize(obj.get(param.getId()));
            } catch (Exception e) {
                throw new JsonParseException(e);
            }
        }
        return config;
    }

    @Override
    public JsonElement serialize(Config src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        src.getParameters().forEach(param -> obj.add(param.getId(), param.serialize()));
        return obj;
    }
}
