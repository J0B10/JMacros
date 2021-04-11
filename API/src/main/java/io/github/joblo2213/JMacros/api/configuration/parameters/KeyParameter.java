package io.github.joblo2213.JMacros.api.configuration.parameters;

import javafx.scene.input.KeyCode;

public class KeyParameter extends EnumParameter<KeyCode> {
    public KeyParameter(String id, String label, String description) {
        super(id, label, description, KeyCode.class);
    }

    public KeyParameter(String id, String label, String description, KeyCode defaultVal) {
        super(id, label, description, defaultVal);
    }
}
