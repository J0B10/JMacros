package de.ungefroren.JMacros.core.config;

import de.ungefroren.JMacros.api.Action;
import de.ungefroren.JMacros.api.Macro;
import de.ungefroren.JMacros.api.configuration.parameters.ColorParameter;
import de.ungefroren.JMacros.api.configuration.InvalidParameterException;
import de.ungefroren.JMacros.api.configuration.parameters.KeyParameter;
import de.ungefroren.JMacros.api.configuration.parameters.Parameter;
import de.ungefroren.JMacros.api.configuration.parameters.PathParameter;
import de.ungefroren.JMacros.api.configuration.parameters.StringParameter;
import de.ungefroren.JMacros.core.JMacros;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MacroData implements Macro {

    private static final Color DEFAULT_COLOR = Color.rgb(60, 64, 68);

    private final StringParameter name = new StringParameter(
            "name",
            "Unique Name",
            "Used for identifying the Macro internally"
    );
    private final KeyParameter keyCode = new KeyParameter(
            "keyCode",
            "Macro Key",
            "Function key that triggers the Macro"
    ){
        @Override
        protected void validate(KeyCode value) throws InvalidParameterException {
            if (!value.isFunctionKey() || value.getCode() > KeyCode.F12.getCode()) {
                throw new InvalidParameterException(keyCode, value.getName() + " is not a function key");
            }
        }
    };
    private final ColorParameter color = new ColorParameter(
            "color",
            "Icon Color",
            "Background color of the Macro's icon",
            DEFAULT_COLOR
    );
    private final PathParameter imageUrl = new PathParameter(
            JMacros.getInstance().getIconsDir(),
            "image",
            "Icon",
            "File location of the image that should be used as icon"
    ); //TODO Proper Parameter type for images

    private final List<Action> actions = new ArrayList<>();

    public String getName() {
        return name.getValue();
    }

    public KeyCode getKeyCode() {
        return keyCode.getValue();
    }

    public Color getColor() {
        return color.getValue();
    }

    public Path getImageUrl() {
        return imageUrl.getValue();
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public Collection<Parameter<?>> getParameters() {
        return Arrays.asList(name, keyCode, color, imageUrl);
    }
}
