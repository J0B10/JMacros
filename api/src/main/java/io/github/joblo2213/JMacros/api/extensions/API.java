package io.github.joblo2213.JMacros.api.extensions;

import io.github.joblo2213.JMacros.api.Action;
import io.github.joblo2213.JMacros.api.Profile;
import io.github.joblo2213.JMacros.api.adapters.Keyboard;
import io.github.joblo2213.JMacros.api.adapters.Mouse;

import java.util.Collection;

public interface API {
    Mouse getMouse();

    Keyboard getKeyboard();

    void registerAction(Class<? extends Action> action);

    Collection<Profile> getProfiles();

    Collection<Profile> getMainProfiles();

    Profile getCurrentProfile();
}
