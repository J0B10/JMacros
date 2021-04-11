package de.ungefroren.JMacros.api.extensions;

import de.ungefroren.JMacros.api.Action;
import de.ungefroren.JMacros.api.Profile;
import de.ungefroren.JMacros.api.adapters.Keyboard;
import de.ungefroren.JMacros.api.adapters.Mouse;

import java.util.Collection;

public interface API {
    Mouse getMouse();

    Keyboard getKeyboard();

    void registerAction(Class<? extends Action> action);

    Collection<Profile> getProfiles();

    Collection<Profile> getMainProfiles();

    Profile getCurrentProfile();
}
