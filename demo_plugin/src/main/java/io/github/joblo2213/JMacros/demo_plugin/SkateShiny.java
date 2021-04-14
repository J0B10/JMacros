package io.github.joblo2213.JMacros.demo_plugin;

import io.github.joblo2213.JMacros.api.API;
import io.github.joblo2213.JMacros.api.Action;
import io.github.joblo2213.JMacros.api.adapters.KeyboardAdapter;
import io.github.joblo2213.JMacros.api.configuration.parameters.Parameter;
import javafx.scene.input.KeyCode;
import org.pf4j.Extension;

import java.util.Collection;
import java.util.List;

@Extension
public class SkateShiny implements Action {

    @Override
    public void run(API api) throws InterruptedException {
        KeyboardAdapter keyboard = api.getKeyboardAdapter();
        keyboard.press(KeyCode.SHIFT);
        keyboard.type(KeyCode.PERIOD);
        keyboard.release(KeyCode.SHIFT);
        keyboard.type(KeyCode.S);
        keyboard.type(KeyCode.K);
        keyboard.type(KeyCode.A);
        keyboard.type(KeyCode.T);
        keyboard.type(KeyCode.E);
        keyboard.press(KeyCode.SHIFT);
        keyboard.type(KeyCode.S);
        keyboard.type(KeyCode.H);
        keyboard.type(KeyCode.I);
        keyboard.type(KeyCode.N);
        keyboard.type(KeyCode.Y);
        keyboard.type(KeyCode.PERIOD);
        keyboard.release(KeyCode.SHIFT);
    }

    @Override
    public Collection<Parameter<?>> getParameters() {
        return List.of();
    }
}
