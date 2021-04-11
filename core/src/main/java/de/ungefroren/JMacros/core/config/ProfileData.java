package de.ungefroren.JMacros.core.config;

import de.ungefroren.JMacros.api.Profile;
import de.ungefroren.JMacros.api.configuration.parameters.BooleanParameter;
import de.ungefroren.JMacros.api.configuration.parameters.IntParameter;
import de.ungefroren.JMacros.api.configuration.parameters.Parameter;
import de.ungefroren.JMacros.api.configuration.parameters.StringParameter;

import java.util.Arrays;
import java.util.Collection;

public class ProfileData implements Profile {
    //FIXME check if two bindings use the same function key

    private final StringParameter name = new StringParameter(
            "name",
            "Name",
            "Name of the profile"
    );
    private final IntParameter id = new IntParameter(
            "id",
            "Profile Number",
            "Each profile has a unique number by which it can be identified"
    );
    private final BooleanParameter main = new BooleanParameter(
            "main",
            "Main profile",
            "Main profiles remain active when closed. " +
                    "You can create multiple main profiles and switch between them depending on your current workflow. " +
                    "All other profiles will go back to the previous profile when closed.",
            false
    );

    private final MacroData[] macroData = new MacroData[12];

    @Override
    public String getName() {
        return name.getValue();
    }

    @Override
    public int getId() {
        return id.getValue();
    }

    @Override
    public boolean isMain() {
        return main.getValue();
    }

    @Override
    public MacroData[] getMacros() {
        return macroData;
    }

    @Override
    public Collection<Parameter<?>> getParameters() {
        return Arrays.asList(name, id, main);
    }
}
