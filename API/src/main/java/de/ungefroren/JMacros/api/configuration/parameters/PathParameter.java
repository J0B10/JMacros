package de.ungefroren.JMacros.api.configuration.parameters;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.ungefroren.JMacros.api.configuration.InvalidParameterException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class PathParameter extends Parameter<Path> {

    private final Path parent;

    public PathParameter(String id, String label, String description) {
        this(null, id, label, description);
    }

    public PathParameter(String id, String label, String description, Path defaultVal) {
        this(null, id, label, description, defaultVal);
    }

    public PathParameter(Path parent, String id, String label, String description) {
        super(id, label, description);
        this.parent = parent;
    }

    public PathParameter(Path parent, String id, String label, String description, Path defaultVal) {
        super(id, label, description, defaultVal);
        this.parent = parent;
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(super.getValue().toString());
    }

    @Override
    public void deserialize(JsonElement json) throws Exception {
        setValue(Paths.get(json.getAsString()));
    }

    @Override
    public Path getValue() {
        return resolve(super.getValue());
    }

    @Override
    protected void validate(Path value) throws InvalidParameterException {
        if (Files.notExists(resolve(value))) throw new InvalidParameterException(this, "file " + value.toString() + " not found");
    }

    private Path resolve(Path path) {
        return Optional.ofNullable(parent).map(p -> p.resolve(path)).orElse(path);
    }
}
