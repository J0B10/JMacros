package io.github.joblo2213.JMacros.core.nativehook;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyEvent;

import java.io.Closeable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NativeHook implements Closeable {

    private static final Logger LOGGER = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    private final HashMap<EventHandler<KeyEvent>, KeyEventAdapter> adapters;

    public NativeHook(Level logLvl) throws NativeHookException {
        LOGGER.setLevel(logLvl);
        GlobalScreen.setEventDispatcher(new VoidDispatchService());
        GlobalScreen.registerNativeHook();
        this.adapters = new HashMap<>();
    }

    public void registerKeyListener(EventHandler<KeyEvent> eventHandler) throws IllegalStateException {
        registerKeyListener(KeyEvent.ANY, eventHandler);
    }

    public void registerKeyListener(EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventHandler) throws IllegalStateException {
        if (adapters.containsKey(eventHandler)) throw new IllegalStateException("event handler is already registered");
        KeyEventAdapter adapter = new KeyEventAdapter(eventType, eventHandler);
        adapters.put(eventHandler, adapter);
        GlobalScreen.addNativeKeyListener(adapter);
    }

    public void unregisterKeyListener(EventHandler<KeyEvent> eventHandler) throws IllegalStateException {
        if (!adapters.containsKey(eventHandler)) throw new IllegalStateException("event handler is not registered");
        GlobalScreen.removeNativeKeyListener(adapters.get(eventHandler));
    }

    @Override
    public void close() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }
}
