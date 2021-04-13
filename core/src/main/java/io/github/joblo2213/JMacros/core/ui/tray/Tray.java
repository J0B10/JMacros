package io.github.joblo2213.JMacros.core.ui.tray;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import io.github.joblo2213.JMacros.core.JMacros;
import io.github.joblo2213.JMacros.core.config.ConfigManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public class Tray implements Closeable {

    public static final Logger logger = LoggerFactory.getLogger(Tray.class);

    private final boolean enabled;

    private final FXTrayIcon tray;

    public Tray(Stage primaryStage) {
        enabled = FXTrayIcon.isSupported();
        if (enabled) {
            tray = new FXTrayIcon(primaryStage, getClass().getResource(JMacros.APPLICATION_ICON_URL));
            tray.setApplicationTitle(JMacros.APPLICATION_NAME);
            tray.setTrayIconTooltip(JMacros.APPLICATION_NAME);
            tray.addExitItem(true);
            tray.addTitleItem(false);
            MenuItem reloadConfig = new MenuItem("Reload config");
            reloadConfig.setOnAction(this::onReloadConfig);
            tray.addMenuItem(reloadConfig);
            tray.show();
            logger.debug("Created tray icon");
        } else {
            logger.error("Could not create tray icon as it's not supported by your current platform");
            tray = null;
        }
    }

    private void onReloadConfig(ActionEvent e){
        Platform.runLater(() -> JMacros.getInstance().reloadConfig());
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void close() {
        if (!enabled) return;
        tray.hide();
    }
}
