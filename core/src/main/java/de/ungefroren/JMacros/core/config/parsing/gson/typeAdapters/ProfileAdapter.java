package de.ungefroren.JMacros.core.config.parsing.gson.typeAdapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.ungefroren.JMacros.api.configuration.parameters.Parameter;
import de.ungefroren.JMacros.core.config.ProfileData;

import java.lang.reflect.Type;
import java.util.Arrays;

public class ProfileAdapter implements JsonSerializer<ProfileData>, JsonDeserializer<ProfileData> {

    @Override
    public JsonElement serialize(ProfileData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        JsonObject parameters = new JsonObject();
        src.getParameters().forEach(param -> parameters.add(param.getId(), param.serialize()));
        obj.add("parameters", parameters);
        JsonArray macros = new JsonArray(src.getMacros().length);
        Arrays.stream(src.getMacros()).forEach(macro -> macros.add(context.serialize(macro)));
        obj.add("macros", macros);
        return obj;
    }

    @Override
    public ProfileData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        ProfileData m = new ProfileData();
        JsonObject parameters = obj.getAsJsonObject("parameters");
        for (Parameter<?> param : m.getParameters()) {
            try {
                param.deserialize(parameters.get(param.getId()));
            } catch (Exception e) {
                throw new JsonParseException(e);
            }
        }
        JsonArray macros = obj.getAsJsonArray("macros");
        if (macros.size() > m.getMacros().length) throw new JsonParseException("too many macros");
        for (int i = 0; i < macros.size(); i++) {
            m.getMacros()[i] = context.deserialize(macros.get(i), MacroAdapter.TYPE);
        }
        return m;
    }
}
