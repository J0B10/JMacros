package de.ungefroren.JMacros.core.ui.editor.parameters;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DoubleParameter {

    @FXML
    private Label description;
    @FXML
    private TextField value;
    @FXML
    private Label key;

    public void setKey(Label key) {
        this.key = key;
    }

    public void setDescription(Label description) {
        this.description = description;
    }

    public TextField getValue() {
        return value;
    }
}
