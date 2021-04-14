package io.github.joblo2213.JMacros.core.action;

import io.github.joblo2213.JMacros.api.API;
import io.github.joblo2213.JMacros.api.Action;
import io.github.joblo2213.JMacros.api.adapters.MouseAdapter;
import io.github.joblo2213.JMacros.api.configuration.parameters.EnumParameter;
import io.github.joblo2213.JMacros.api.configuration.parameters.Parameter;
import org.pf4j.Extension;

import java.util.Collection;
import java.util.List;

@Extension
public class MouseButton implements Action {

    private final EnumParameter<javafx.scene.input.MouseButton> button = new EnumParameter<>(
            "button",
            "Button",
            "primary (left mouse button) / middle / secondary (right mouse button) / back / forward",
            javafx.scene.input.MouseButton.PRIMARY
    );

    private final EnumParameter<Operation> operation = new EnumParameter<>(
            "operation",
            "Operation",
            "press / release / click",
            Operation.CLICK
    );

    @Override
    public void run(API api) {
        MouseAdapter mouse = api.getMouseAdapter();
        switch (operation.getValue()) {
            case PRESS:
                mouse.press(button.getValue());
                break;
            case RELEASE:
                mouse.release(button.getValue());
                break;
            case CLICK:
                mouse.click(button.getValue());
                break;
            default:
                throw new NullPointerException();
        }
    }

    @Override
    public Collection<Parameter<?>> getParameters() {
        return List.of(button, operation);
    }

    public enum Operation {
        PRESS, RELEASE, CLICK
    }
}
