package io.github.joblo2213.JMacros.core.plugins;

import io.github.joblo2213.JMacros.api.API;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginFactory;

import java.nio.file.Path;

public class JMacrosPluginManager extends DefaultPluginManager {

    private final API api;

    public JMacrosPluginManager(API api, Path... pluginsRoots) {
        super(pluginsRoots);
        this.api = api;
    }

    @Override
    protected PluginFactory createPluginFactory() {
        return new JMacrosPluginFactory(api);
    }
}
