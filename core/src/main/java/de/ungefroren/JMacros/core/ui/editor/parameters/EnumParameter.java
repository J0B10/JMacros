package de.ungefroren.JMacros.core.ui.editor.parameters;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class EnumParameter<E extends Enum<E>> {

    @FXML
    private Label description;
    @FXML
    private ComboBox<E> value;
    @FXML
    private Label key;

    public void setKey(Label key) {
        this.key = key;
    }

    public void setDescription(Label description) {
        this.description = description;
    }

    public ComboBox<E> getValue() {
        return value;
    }
}
