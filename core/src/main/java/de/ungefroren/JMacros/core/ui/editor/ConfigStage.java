package de.ungefroren.JMacros.core.ui.editor;

import de.ungefroren.JMacros.core.JMacros;
import de.ungefroren.JMacros.core.ui.editor.parameters.KeyParameter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ConfigStage extends Stage {

    private static final String FXML_DOCUMENT = "/de/ungefroren/JMacros/core/ui/editor/config.fxml";

    public ConfigStage() {
        initStyle(StageStyle.UNDECORATED);
        setTitle(JMacros.APPLICATION_NAME + " - Settings");
        setMinHeight(360);
        setMinWidth(270);
        getIcons().add(new Image(getClass().getResourceAsStream(JMacros.APPLICATION_ICON_URL)));
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent load = fxmlLoader.load(getClass().getResourceAsStream(FXML_DOCUMENT));
            setScene(new Scene(load));
            ConfigController c = fxmlLoader.getController();
            FXMLLoader keyParam = new FXMLLoader();
            Parent load1 = keyParam.load(getClass().getResourceAsStream("/de/ungefroren/JMacros/core/ui/editor/parameters/keyParameter.fxml"));
            KeyParameter key = keyParam.getController();
            c.settingsList.getChildren().add(load1);
            c.exitButton.requestFocus();
            key.setKey("test");
            key.setDescription("test key");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
