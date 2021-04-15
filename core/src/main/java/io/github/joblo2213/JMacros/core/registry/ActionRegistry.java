package io.github.joblo2213.JMacros.core.registry;

import io.github.joblo2213.JMacros.api.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ActionRegistry {

    private static final HashMap<String, Class<? extends Action>> registry = new HashMap<>();
    public static Logger logger = LoggerFactory.getLogger(ActionRegistry.class);

    public static void register(Class<? extends Action> action) throws IllegalArgumentException {
        try {
            action.getConstructor().newInstance();
            registry.put(action.getName(), action);
            logger.debug("Registered action '{}'", action.getName());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(action.getName() + " does not define a public no-argument constructor", e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(action.getName() + " is abstract", e);
        }
    }

    public static Action initialize(Class<? extends Action> action) {
        try {
            return action.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
            //TODO improved Exception Handling
        }
    }

    public static Action initialize(String className) throws IllegalArgumentException {
        Class<? extends  Action> action = registry.get(className);
        if (action == null) throw new IllegalArgumentException(className + " is not registered");
        return initialize(action);
    }

    public static String getName(Action action) {
        return action.getClass().getName();
    }
}
