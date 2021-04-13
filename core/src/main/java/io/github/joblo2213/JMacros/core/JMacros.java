package io.github.joblo2213.JMacros.core;

import io.github.joblo2213.JMacros.core.action.Key;
import io.github.joblo2213.JMacros.core.config.Config;
import io.github.joblo2213.JMacros.core.config.ConfigManager;
import io.github.joblo2213.JMacros.core.config.MacroData;
import io.github.joblo2213.JMacros.core.config.ProfileData;
import io.github.joblo2213.JMacros.core.nativehook.NativeHook;
import io.github.joblo2213.JMacros.core.registry.ActionRegistry;
import io.github.joblo2213.JMacros.core.ui.overlay.Overlay;
import io.github.joblo2213.JMacros.core.ui.tray.Tray;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class JMacros extends Application {

    public static final String APPLICATION_NAME = "JMacros";
    public static final String APPLICATION_ICON_URL = "/io/github/joblo2213/JMacros/core/ui/icons/icon.png";

    private static JMacros instance;

    private Robot robot;
    private ConfigManager configManager;
    private NativeHook nativeHook;
    private Overlay overlay;
    private ActionsExecutor actionsExecutor;
    private Tray tray;

    private volatile ProfileData currentProfile;
    private volatile ApplicationState state = ApplicationState.INIT;

    public static JMacros getInstance() {
        return instance;
    }

    @Override
    public void init() throws Exception {
        instance = this;
        ActionRegistry.register(Key.class);

        boolean isPortable = getParameters().getUnnamed().contains("portable");
        configManager = new ConfigManager(isPortable);
        configManager.saveDefaultConfig();
        Platform.setImplicitExit(false);
    }

    @Override
    public void start(Stage stage) throws Exception {
        state = ApplicationState.STARTUP;
        actionsExecutor = new ActionsExecutor();
        configManager.loadConfig();
        configManager.loadProfiles();
        currentProfile = configManager.getDefaultProfile().orElse(null);
        tray = new Tray(stage);
        robot = new Robot();
        nativeHook = new NativeHook(Level.WARNING);
        overlay = new Overlay(stage, getConfig().getScale());
        MacroHandler macroHandler = new MacroHandler(this);
        nativeHook.registerKeyListener(KeyEvent.KEY_PRESSED, macroHandler);
        state = ApplicationState.PASSIVE;
    }

    @Override
    public void stop() throws Exception {
        state = ApplicationState.SHUTDOWN;
        actionsExecutor.shutdown();
        nativeHook.close();
        tray.close();
        try {
            boolean shutdown = actionsExecutor.awaitTermination(5, TimeUnit.SECONDS);
            if (!shutdown) actionsExecutor.shutdownNow();
        } catch (InterruptedException ignored) {
        }
    }

    public void reloadConfig() {
        boolean overlayShown = getState() == ApplicationState.OVERLAY;
        Optional<Integer> currentProfileID = Optional.ofNullable(currentProfile).map(ProfileData::getId);
        if (overlayShown) showOverlay(false);
        configManager.loadConfig();
        configManager.loadProfiles();
        overlay.setScale(getConfig().getScale());
        currentProfile = currentProfileID.flatMap(configManager::getProfile).or(configManager::getDefaultProfile).orElse(null);
        if (overlayShown && currentProfile != null) showOverlay(true);
    }

    public Robot getRobot() {
        return robot;
    }

    public Config getConfig() {
        return configManager.getConfig();
    }

    public List<ProfileData> getProfiles() {
        return configManager.getProfiles();
    }

    public synchronized Optional<ProfileData> getCurrentProfile() {
        return Optional.ofNullable(currentProfile);
    }

    private synchronized void setCurrentProfile(ProfileData currentProfile) {
        this.currentProfile = currentProfile;
    }

    public boolean performAction(KeyCode functionKey) {
        if (!functionKey.isFunctionKey())
            throw new IllegalArgumentException(functionKey.getName() + " is not a function key");
        if (getCurrentProfile().isEmpty())
            throw new IllegalStateException("no profile active");
        Optional<MacroData> macro = Arrays.stream(getCurrentProfile().get().getMacros())
                .filter(m -> m != null && m.getKeyCode() == functionKey).findAny();
        if (macro.isEmpty()) return false;
        actionsExecutor.runActions(macro.get());
        return true;
    }

    public void showOverlay(boolean show) {
        Platform.runLater(() -> {
            try {
                ApplicationState state = getState();
                if (state != ApplicationState.PASSIVE && state != ApplicationState.OVERLAY)
                    throw new IllegalStateException(state.toString());
                if (show) {
                    if (getCurrentProfile().isEmpty()) return;
                    setState(ApplicationState.OVERLAY);
                    overlay.setProfile(getCurrentProfile().get());
                    overlay.show();
                } else {
                    setState(ApplicationState.PASSIVE);
                    overlay.hide();
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        });
    }

    public synchronized ApplicationState getState() {
        return state;
    }

    private synchronized void setState(ApplicationState state) {
        this.state = state;
    }

    public Path getIconsDir() {
        return configManager.getIconsDir();
    }
}