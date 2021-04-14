package io.github.joblo2213.JMacros.core.plugins;

import io.github.joblo2213.JMacros.api.API;
import io.github.joblo2213.JMacros.api.Plugin;
import org.pf4j.DefaultPluginFactory;
import org.pf4j.PluginFactory;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class JMacrosPluginFactory implements PluginFactory {

    public static final Logger logger = LoggerFactory.getLogger(JMacrosPluginFactory.class);

    private final API api;

    public JMacrosPluginFactory(API api) {
        this.api = api;
    }

    /**
     * Creates a plugin instance. If an error occurs than that error is logged and the method returns null.
     * <p>
     * This method has been copied from {@link DefaultPluginFactory#create(PluginWrapper)} and adapted to pass a
     * reference of the API to the plugin
     *
     * @param pluginWrapper the wrapper of the plugin that should be instantiated
     * @return the Plugin instance
     */
    @Override
    public org.pf4j.Plugin create(PluginWrapper pluginWrapper) {
        String pluginClassName = pluginWrapper.getDescriptor().getPluginClass();
        logger.debug("Create instance for plugin '{}'", pluginClassName);

        Class<?> pluginClass;
        try {
            pluginClass = pluginWrapper.getPluginClassLoader().loadClass(pluginClassName);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

        // once we have the class, we can do some checks on it to ensure
        // that it is a valid implementation of a plugin.
        int modifiers = pluginClass.getModifiers();
        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)
                || (!org.pf4j.Plugin.class.isAssignableFrom(pluginClass))) {
            logger.error("The plugin class '{}' is not valid", pluginClassName);
            return null;
        }

        // create the plugin instance
        try {
            if (Plugin.class.isAssignableFrom(pluginClass)) {
                Constructor<?> constructor = pluginClass.getConstructor(PluginWrapper.class, API.class);
                return (Plugin) constructor.newInstance(pluginWrapper, api);
            } else {
                Constructor<?> constructor = pluginClass.getConstructor(PluginWrapper.class);
                return (org.pf4j.Plugin) constructor.newInstance(pluginWrapper);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}
