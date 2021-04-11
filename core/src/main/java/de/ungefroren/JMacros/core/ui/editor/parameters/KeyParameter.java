package de.ungefroren.JMacros.core.ui.editor.parameters;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class KeyParameter implements Parameter<KeyCode> {

    private boolean acceptInput = false;

    @FXML
    private Label description;
    @FXML
    private TextField value;
    @FXML
    private Label key;

    private KeyCode code;

    public void setKey(String key) {
        this.key.setText(key);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public KeyCode getValue() {
        return code;
    }

    public void onKeyPress(KeyEvent keyEvent) {
        if (!acceptInput) return;
        acceptInput = false;
        code = keyEvent.getCode();
        key.requestFocus();
        value.setText(code.getName());
        value.setPromptText("");
        System.out.println(code);
    }

    public void onAction(ActionEvent event) {
        if (acceptInput) return;
        code = null;
        value.requestFocus();
        value.setText("press any key");
        value.setPromptText("press any key");
        Platform.runLater(() -> acceptInput = true);
    }
}
