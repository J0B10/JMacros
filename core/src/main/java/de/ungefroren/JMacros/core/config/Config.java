package de.ungefroren.JMacros.core.config;

import de.ungefroren.JMacros.api.configuration.Configurable;
import de.ungefroren.JMacros.api.configuration.parameters.DoubleParameter;
import de.ungefroren.JMacros.api.configuration.parameters.IntParameter;
import de.ungefroren.JMacros.api.configuration.parameters.KeyParameter;
import de.ungefroren.JMacros.api.configuration.parameters.Parameter;
import javafx.scene.input.KeyCode;

import java.util.Arrays;
import java.util.Collection;

public class Config implements Configurable {

    private final KeyParameter toggleKey = new KeyParameter(
            "toggleKey",
            "toggleKey",
            "no description provided",
            KeyCode.CAPS
    );
    private final IntParameter defaultProfile = new IntParameter(
            "defaultProfile",
            "Default profile",
            "Profile that is active by default",
            0
    ); //TODO Parameter for selecting existing profiles
    private final DoubleParameter scale = new DoubleParameter(
            "scale",
            "Scale",
            "Size of the macro icons in pixels",
            64
    );

    public KeyCode getToggleKey() {
        return toggleKey.getValue();
    }

    public int getDefaultProfile() {
        return defaultProfile.getValue();
    }

    public double getScale() {
        return scale.getValue();
    }

    @Override
    public Collection<Parameter<?>> getParameters() {
        return Arrays.asList(toggleKey, defaultProfile, scale);
    }
}
