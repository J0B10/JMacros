package de.ungefroren.JMacros.core.ui.editor.parameters;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;

public class BooleanParameter {
    @FXML
    private Label description;
    @FXML
    private CheckBox value;
    @FXML
    private Label key;

    public void setKey(Label key) {
        this.key = key;
    }

    public void setDescription(Label description) {
        this.description = description;
    }

    public CheckBox getValue() {
        return value;
    }
}
