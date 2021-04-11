package de.ungefroren.JMacros.core.ui.editor.parameters;

public interface Parameter<T> {

    void setKey(String key);

    void setDescription(String description);

    T getValue();
}
