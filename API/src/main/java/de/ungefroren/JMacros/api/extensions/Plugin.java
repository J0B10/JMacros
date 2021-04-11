package de.ungefroren.JMacros.api.extensions;

public interface Plugin {

    void load(API api);

    void unload();
}
