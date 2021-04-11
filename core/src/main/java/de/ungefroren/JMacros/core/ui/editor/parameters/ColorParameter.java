package de.ungefroren.JMacros.core.ui.editor.parameters;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;

public class ColorParameter {
    @FXML
    private Label description;
    @FXML
    private ColorPicker value;
    @FXML
    private Label key;

    public void setKey(Label key) {
        this.key = key;
    }

    public void setDescription(Label description) {
        this.description = description;
    }

    public ColorPicker getValue() {
        return value;
    }
}
