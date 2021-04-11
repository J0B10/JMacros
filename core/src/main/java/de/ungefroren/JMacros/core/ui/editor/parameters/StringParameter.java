package de.ungefroren.JMacros.core.ui.editor.parameters;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StringParameter implements Parameter<String> {

    @FXML
    private Label description;
    @FXML
    private TextField value;
    @FXML
    private Label key;

    public void setKey(String key) {
        this.key.setText(key);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public String getValue() {
        return value.getText();
    }
}
