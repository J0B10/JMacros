package io.github.joblo2213.JMacros.api;

import org.pf4j.PluginWrapper;

public abstract class Plugin extends org.pf4j.Plugin {

    protected final API api;

    public Plugin(PluginWrapper wrapper, API api) {
        super(wrapper);
        this.api = api;
    }
}
