package io.github.joblo2213.JMacros.core.ui.tray;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import io.github.joblo2213.JMacros.core.JMacros;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class Tray {


    public Tray(Stage primaryStage) {
        FXTrayIcon tray = new FXTrayIcon(primaryStage, getClass().getResource(JMacros.APPLICATION_ICON_URL));
        tray.setApplicationTitle(JMacros.APPLICATION_NAME);
        tray.setTrayIconTooltip(JMacros.APPLICATION_NAME);
        tray.addExitItem(true);
        MenuItem reloadConfig = new MenuItem("Reload config");
        reloadConfig.addEventHandler(ActionEvent.ACTION, this::onReloadConfig);
        tray.addMenuItem(reloadConfig);
        tray.show();
    }

    private void onReloadConfig(ActionEvent e){
        Platform.runLater(() -> JMacros.getInstance().reloadConfig());
    }
}
