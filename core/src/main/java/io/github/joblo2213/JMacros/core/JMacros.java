package io.github.joblo2213.JMacros.core;

import io.github.joblo2213.JMacros.api.API;
import io.github.joblo2213.JMacros.api.Action;
import io.github.joblo2213.JMacros.api.Macro;
import io.github.joblo2213.JMacros.api.Profile;
import io.github.joblo2213.JMacros.core.config.Config;
import io.github.joblo2213.JMacros.core.config.ConfigManager;
import io.github.joblo2213.JMacros.core.nativehook.NativeHook;
import io.github.joblo2213.JMacros.core.plugins.JMacrosPluginManager;
import io.github.joblo2213.JMacros.core.registry.ActionRegistry;
import io.github.joblo2213.JMacros.core.ui.overlay.Overlay;
import io.github.joblo2213.JMacros.core.ui.tray.Tray;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class JMacros extends Application {

    public static final Logger logger = LoggerFactory.getLogger(JMacros.class);

    public static final String APPLICATION_THREAD_NAME = "JMacros-FX";
    public static final String APPLICATION_NAME = "JMacros";
    public static final String APPLICATION_ICON_URL = "/io/github/joblo2213/JMacros/core/ui/icons/icon.png";

    private static JMacros instance;

    private Robot robot;
    private ConfigManager configManager;
    private JMacrosPluginManager pluginManager;
    private NativeHook nativeHook;
    private Overlay overlay;
    private ActionsExecutor actionsExecutor;
    private Tray tray;
    private API api;

    private volatile Profile currentProfile;
    private volatile ApplicationState state = ApplicationState.INIT;

    public static JMacros getInstance() {
        return instance;
    }

    @Override
    public void init() {
        instance = this;
    }

    @Override
    public void start(Stage stage) throws Exception {
        setState(ApplicationState.STARTUP);
        Thread.currentThread().setName(APPLICATION_THREAD_NAME);
        Platform.setImplicitExit(false);

        boolean isPortable = getParameters().getUnnamed().contains("portable");
        configManager = new ConfigManager(isPortable);
        configManager.saveDefaultConfig();
        configManager.loadConfig();

        logger.debug("Setting up Plugin API");
        robot = new Robot();
        api = new APIProvider(this);
        pluginManager = new JMacrosPluginManager(api, configManager.getPluginsDir());

        logger.info("Loading plugins...");
        pluginManager.loadPlugins();
        logger.info("Starting plugins...");
        pluginManager.startPlugins();
        logger.info("Loading actions...");
        pluginManager.getExtensionClasses(Action.class).forEach(ActionRegistry::register);

        configManager.loadProfiles();
        currentProfile = configManager.getDefaultProfile().orElse(null);
        if (currentProfile == null) logger.info("No default profile defined");

        logger.debug("Adding tray icon");
        tray = new Tray(stage);
        logger.debug("Setting up overlay");
        overlay = new Overlay(stage, getConfig().getScale());

        logger.debug("Starting actions executor");
        actionsExecutor = new ActionsExecutor(api);

        logger.debug("Adding native hook.");
        nativeHook = new NativeHook(Level.WARNING);
        MacroHandler macroHandler = new MacroHandler(this);
        nativeHook.registerKeyListener(KeyEvent.KEY_PRESSED, macroHandler);

        logger.info("Startup complete. Have fun!");
        setState(ApplicationState.PASSIVE);
    }

    @Override
    public void stop() {
        setState(ApplicationState.SHUTDOWN);
        logger.debug("Stop executing new actions");
        actionsExecutor.shutdown();
        logger.debug("Unregistering native hook");
        nativeHook.close();
        logger.debug("Removing tray icon");
        tray.close();
        actionsExecutor.terminateExecution(10, TimeUnit.SECONDS);
        logger.info("Stopping plugins...");
        pluginManager.stopPlugins();
        logger.info("THE END.");
    }

    public void reloadConfig() {
        logger.info("Reloading config...");
        boolean overlayShown = getState() == ApplicationState.OVERLAY;
        Optional<Integer> currentProfileID = Optional.ofNullable(currentProfile).map(Profile::getId);
        if (overlayShown) showOverlay(false);
        configManager.loadConfig();
        configManager.loadProfiles();
        overlay.setScale(getConfig().getScale());
        currentProfile = currentProfileID.flatMap(configManager::getProfile).or(configManager::getDefaultProfile).orElse(null);
        logger.info("Reload complete.");
        if (overlayShown && currentProfile != null) showOverlay(true);
    }

    public boolean performAction(KeyCode functionKey) {
        if (!functionKey.isFunctionKey())
            throw new IllegalArgumentException(functionKey.getName() + " is not a function key");
        if (getCurrentProfile().isEmpty())
            throw new IllegalStateException("no profile active");
        Optional<Macro> macro = Arrays.stream(getCurrentProfile().get().getMacros())
                .filter(m -> m != null && m.getKeyCode() == functionKey).findAny();
        if (macro.isEmpty()) {
            logger.debug("No macro exists for key " + functionKey.getName());
            return false;
        }
        logger.debug("Submitted actions for macro {} for execution", macro.get().getName());
        actionsExecutor.runActions(macro.get());
        return true;
    }

    public void showOverlay(boolean show) {
        ApplicationState state = getState();
        if (state != ApplicationState.PASSIVE && state != ApplicationState.OVERLAY)
            throw new IllegalStateException(state.toString());
        if (show) {
            if (getCurrentProfile().isEmpty()) {
                logger.debug("Can't show overlay: No profile defined.");
                return;
            }
            setState(ApplicationState.OVERLAY);
            Platform.runLater(() -> {
                overlay.setProfile(getCurrentProfile().get());
                overlay.show();
            });
        } else {
            setState(ApplicationState.PASSIVE);
            Platform.runLater(() -> overlay.hide());
        }
    }

    public API getApi() {
        return api;
    }

    public Robot getRobot() {
        return robot;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Config getConfig() {
        return configManager.getConfig();
    }

    public synchronized Optional<Profile> getCurrentProfile() {
        return Optional.ofNullable(currentProfile);
    }

    public synchronized void setCurrentProfile(Profile currentProfile) {
        Objects.requireNonNull(currentProfile);
        boolean change = currentProfile != this.currentProfile;
        this.currentProfile = currentProfile;
        if (change && state == ApplicationState.OVERLAY) {
            Platform.runLater(() -> overlay.setProfile(currentProfile));
        }
    }

    public synchronized ApplicationState getState() {
        return state;
    }

    private synchronized void setState(ApplicationState state) {
        this.state = state;
    }
}