package io.github.joblo2213.JMacros.core;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class MacroHandler implements EventHandler<KeyEvent> {

    private final JMacros jMacros;

    public MacroHandler(JMacros jMacros) {
        this.jMacros = jMacros;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == jMacros.getConfig().getToggleKey()) {
            switch (jMacros.getState()) {
                case PASSIVE:
                    jMacros.showOverlay(true);
                    keyEvent.consume();
                    break;
                case OVERLAY:
                    jMacros.showOverlay(false);
                    keyEvent.consume();
                    break;
                default:
                    break;
            }
        } else if (keyEvent.getCode().isFunctionKey() && jMacros.getState() == ApplicationState.OVERLAY){
            boolean result = jMacros.performAction(keyEvent.getCode());
            if (result) {
                jMacros.showOverlay(false);
            }
            keyEvent.consume();
        }
    }
}
