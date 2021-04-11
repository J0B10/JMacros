package io.github.joblo2213.JMacros.core.action;

import io.github.joblo2213.JMacros.api.Action;
import io.github.joblo2213.JMacros.api.configuration.parameters.EnumParameter;
import io.github.joblo2213.JMacros.api.configuration.parameters.KeyParameter;
import io.github.joblo2213.JMacros.api.configuration.parameters.Parameter;
import io.github.joblo2213.JMacros.core.JMacros;
import javafx.application.Platform;
import javafx.scene.robot.Robot;

import java.util.Collection;
import java.util.List;

public class Key implements Action {

    private final KeyParameter key = new KeyParameter(
            "key",
            "Key",
            "The key that is pressed/released"
    );
    private final EnumParameter<Operation> operation = new EnumParameter<Operation>(
            "operation",
            "Operation",
            "pressed / released / typed",
            Operation.TYPED
    );

    @Override
    public void run() throws InterruptedException {
        Platform.runLater(() -> {
            Robot robot = JMacros.getInstance().getRobot();
            switch (operation.getValue()) {
                case PRESSED:
                    robot.keyPress(key.getValue());
                    break;
                case RELEASED:
                    robot.keyRelease(key.getValue());
                    break;
                case TYPED:
                    robot.keyType(key.getValue());
                    break;
                default:
                    throw new NullPointerException();
            }
        });
    }

    @Override
    public Collection<Parameter<?>> getParameters() {
        return List.of(key, operation);
    }

    @SuppressWarnings("unused")
    public enum Operation {
        PRESSED, RELEASED, TYPED;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
