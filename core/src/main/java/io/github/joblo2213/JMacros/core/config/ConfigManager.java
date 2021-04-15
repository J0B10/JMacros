package io.github.joblo2213.JMacros.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import io.github.joblo2213.JMacros.api.Action;
import io.github.joblo2213.JMacros.core.config.parsing.gson.typeAdapters.ActionAdapter;
import io.github.joblo2213.JMacros.core.config.parsing.gson.typeAdapters.ConfigAdapter;
import io.github.joblo2213.JMacros.core.config.parsing.gson.typeAdapters.MacroAdapter;
import io.github.joblo2213.JMacros.core.config.parsing.gson.typeAdapters.ProfileAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ConfigManager {
    public static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Action.class, new ActionAdapter())
            .registerTypeAdapter(MacroData.class, new MacroAdapter())
            .registerTypeAdapter(ProfileData.class, new ProfileAdapter())
            .registerTypeAdapter(Config.class, new ConfigAdapter())
            .setLenient()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private final Path configDir;
    private final Path configFile;
    private final Path profilesDir;
    private final Path iconsDir;
    private final Path pluginsDir;

    private final HashMap<Integer, Path> profileConfigFiles = new HashMap<>();
    private final List<ProfileData> profiles = new ArrayList<>();
    private Config config = new Config();

    public ConfigManager(boolean isPortable) {
        if (isPortable) {
            configDir = Paths.get(System.getProperty("user.dir"));
            logger.debug("Started in portable mode.");
        } else {
            Path configDir;
            configDir = Paths.get(System.getProperty("user.home")).resolve(".jmacros");
            if (System.getProperty("os.name").startsWith("mac"))
                configDir = Paths.get(System.getProperty("user.home")).resolve("Library/Application Support/jmacros");

            String xdgConfigHome = System.getenv("XDG_CONFIG_HOME");
            try {
                if (xdgConfigHome != null && !xdgConfigHome.isBlank()) {
                    configDir = Paths.get(xdgConfigHome).resolve("jmacros");
                    logger.debug("Using $XDG_CONFIG_HOME for configs");
                } else {
                    logger.debug("Using user home dir for configs");
                }
            } catch (InvalidPathException e) {
                logger.warn("XDG_CONFIG_HOME environment variable does not point to a valid path");
                logger.warn(e.getMessage());
                logger.debug(e.getMessage(), e);
                logger.info("Falling back to legacy dir");
            }

            this.configDir = configDir;
        }

        logger.info("Config dir: " + configDir);
        configFile = configDir.resolve("config.json");
        profilesDir = configDir.resolve("profiles");
        iconsDir = configDir.resolve("icons");
        pluginsDir = configDir.resolve("plugins");
    }


    public void saveDefaultConfig() {
        try {
            if (!Files.exists(configDir)) Files.createDirectories(configDir);
            if (!Files.exists(configFile)) {
                try (BufferedWriter bw = Files.newBufferedWriter(configFile, StandardCharsets.UTF_8)) {
                    config = new Config();
                    gson.toJson(config, bw);
                    logger.info("Saved default config.");
                }
            }
            if (!Files.exists(profilesDir)) Files.createDirectories(profilesDir);
            if (!Files.exists(iconsDir)) Files.createDirectories(iconsDir);
            if (!Files.exists(pluginsDir)) Files.createDirectories(pluginsDir);
        } catch (IOException e) {
            logger.error("Could not create default config: ");
            logger.error(e.getMessage());
            logger.debug(e.getMessage(), e);
            //TODO Exception handling
        }

    }

    public void saveProfiles() {
        logger.info("Saving profiles...");
        for (ProfileData profile : profiles) {
            Path path = profileConfigFiles.get(profile.getId());
            try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                gson.toJson(profile, bw);
                logger.debug("Saved profile '{}'.", profile.getName());
            } catch (IOException | JsonParseException e) {
                logger.warn("Could not save profile '{}': ", path.getFileName().toString().replaceAll("\\.json$", ""));
                logger.warn(e.getMessage());
                logger.debug(e.getMessage(), e);
                //TODO Exception handling
            }
        }
    }

    public void loadProfiles() {
        profiles.clear();
        profileConfigFiles.clear();
        logger.info("Loading profiles...");
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.json");
        try {
            Files.walk(profilesDir).filter(matcher::matches).forEach(path -> {
                try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                    ProfileData profile = gson.fromJson(br, ProfileData.class);
                    if (profileConfigFiles.containsKey(profile.getId()))
                        throw new JsonParseException("profile with id " + profile.getId() + " already exists");
                    profileConfigFiles.put(profile.getId(), path);
                    profiles.add(profile);
                    logger.debug("Loaded profile '{}'.", profile.getName());
                } catch (IOException | JsonParseException e) {
                    logger.warn("Could not load profile '{}': ", path.getFileName().toString().replaceAll("\\.json$", ""));
                    logger.warn(e.getMessage());
                    logger.debug(e.getMessage(), e);
                }
            });
            logger.info("Loaded " + profiles.size() + " profiles.");
        } catch (IOException e) {
            logger.error("Could not list profiles: ");
            logger.error(e.getMessage());
            logger.debug(e.getMessage(), e);
            //TODO Exception handling
        }
    }

    public void saveConfig() {
        logger.info("Saving config...");
        try (BufferedWriter bw = Files.newBufferedWriter(configFile, StandardCharsets.UTF_8)) {
            gson.toJson(config, bw);
            logger.debug("Saved config.");
        } catch (IOException | JsonParseException e) {
            logger.warn("Could not save config: ");
            logger.warn(e.getMessage());
            logger.debug(e.getMessage(), e);
            //TODO Exception handling
        }
    }

    public void loadConfig() {
        logger.info("Loading config...");
        try (BufferedReader br = Files.newBufferedReader(configFile, StandardCharsets.UTF_8)) {
            config = gson.fromJson(br, Config.class);
        } catch (IOException | JsonParseException e) {
            logger.warn("Could not load config: ");
            logger.warn(e.getMessage());
            logger.debug(e.getMessage(), e);
            //TODO Exception handling
        }
    }

    public List<ProfileData> getProfiles() {
        return profiles;
    }

    public Optional<ProfileData> getProfile(int id) {
        return getProfiles().stream().filter(profile -> profile.getId() == id).findAny();
    }

    public Optional<ProfileData> getDefaultProfile() {
        return getProfile(config.getDefaultProfile());
    }

    public Config getConfig() {
        return config;
    }

    public Path getIconsDir() {
        return iconsDir;
    }

    public Path getPluginsDir() {
        return pluginsDir;
    }
}
