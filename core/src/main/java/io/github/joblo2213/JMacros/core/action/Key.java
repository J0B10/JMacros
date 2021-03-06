package io.github.joblo2213.JMacros.core.action;

import io.github.joblo2213.JMacros.api.API;
import io.github.joblo2213.JMacros.api.Action;
import io.github.joblo2213.JMacros.api.adapters.KeyboardAdapter;
import io.github.joblo2213.JMacros.api.configuration.parameters.EnumParameter;
import io.github.joblo2213.JMacros.api.configuration.parameters.KeyParameter;
import io.github.joblo2213.JMacros.api.configuration.parameters.Parameter;
import org.pf4j.Extension;

import java.util.Collection;
import java.util.List;

@Extension
public class Key implements Action {

    private final KeyParameter key = new KeyParameter(
            "key",
            "Key",
            "The key that is pressed/released"
    );
    private final EnumParameter<Operation> operation = new EnumParameter<>(
            "operation",
            "Operation",
            "press / release / type",
            Operation.TYPE
    );

    @Override
    public void run(API api) {
        KeyboardAdapter keyboard = api.getKeyboardAdapter();
        switch (operation.getValue()) {
            case PRESS:
                keyboard.press(key.getValue());
                break;
            case RELEASE:
                keyboard.release(key.getValue());
                break;
            case TYPE:
                keyboard.type(key.getValue());
                break;
            default:
                throw new NullPointerException();
        }
    }

    @Override
    public Collection<Parameter<?>> getParameters() {
        return List.of(key, operation);
    }

    public enum Operation {
        PRESS, RELEASE, TYPE
    }
}
