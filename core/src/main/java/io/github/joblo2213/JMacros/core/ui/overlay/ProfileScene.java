package io.github.joblo2213.JMacros.core.ui.overlay;

import io.github.joblo2213.JMacros.api.Macro;
import io.github.joblo2213.JMacros.api.Profile;
import io.github.joblo2213.JMacros.core.utils.FunctionKey;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class ProfileScene extends Scene {

    public ProfileScene(Profile data, DoubleProperty scale) {
        super(new HBox(), Color.TRANSPARENT);
        HBox root = (HBox) getRoot();
        root.setBackground(Background.EMPTY);
        Macro[] macros = data.getMacros();
        MacroIcon[] icons = new MacroIcon[12];
        for (int i = 0; i < 12; i++) {
            if (i < macros.length && macros[i] != null) {
                icons[i] = new MacroIcon(macros[i], scale);
            } else {
                icons[i] = new MacroIcon(FunctionKey.f(i + 1), null, Color.rgb(60, 64, 68), scale);
            }
        }
        //FIXME Bindings must be assigned in the right order to the right function key, duplicates must throw errors
        root.setPickOnBounds(false);
        root.spacingProperty().bind(scale.divide(4));
        Arrays.stream(icons).forEach(root.getChildren()::add);
    }
}
