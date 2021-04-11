package io.github.joblo2213.JMacros.core.ui.tray;

import io.github.joblo2213.JMacros.core.JMacros;
import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import javafx.application.Platform;

import java.awt.event.ActionEvent;
import java.io.Closeable;
import java.util.Objects;

public class Tray implements Closeable {

    private final SystemTray tray;

    public Tray() {
        tray = SystemTray.get();
        if (tray == null) throw new RuntimeException("Unable to load SystemTray!");
        tray.setStatus(JMacros.APPLICATION_NAME);
        tray.setImage(Objects.requireNonNull(getClass().getResourceAsStream(JMacros.APPLICATION_ICON_URL)));
        Menu mainMenu = tray.getMenu();
        mainMenu.add(new MenuItem("Reload config", this::onReloadConfig));
        mainMenu.add(new MenuItem("Quit", this::onQuit));
    }

    private void onQuit(ActionEvent e) {
        Platform.exit();
    }

    private void onReloadConfig(ActionEvent e){
        Platform.runLater(() -> JMacros.getInstance().reloadConfig());
    }

    @Override
    public void close() {
        tray.shutdown();
    }
}
