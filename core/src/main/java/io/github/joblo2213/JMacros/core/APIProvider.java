package io.github.joblo2213.JMacros.core;

import io.github.joblo2213.JMacros.api.API;
import io.github.joblo2213.JMacros.api.adapters.KeyboardAdapter;
import io.github.joblo2213.JMacros.api.adapters.MouseAdapter;
import io.github.joblo2213.JMacros.api.adapters.ProfileAdapter;
import io.github.joblo2213.JMacros.core.adapters.FxKeyAdapter;
import io.github.joblo2213.JMacros.core.adapters.FxMouseAdapter;
import io.github.joblo2213.JMacros.core.adapters.ProfileDataAdapter;

public class APIProvider implements API {

    private final KeyboardAdapter keyboardAdapter;
    private final MouseAdapter mouseAdapter;
    private final ProfileAdapter profileAdapter;

    public APIProvider(JMacros jMacros) {
        keyboardAdapter = new FxKeyAdapter(jMacros.getRobot());
        mouseAdapter = new FxMouseAdapter(jMacros.getRobot());
        profileAdapter = new ProfileDataAdapter(jMacros);
    }

    @Override
    public MouseAdapter getMouseAdapter() {
        return mouseAdapter;
    }

    @Override
    public KeyboardAdapter getKeyboardAdapter() {
        return keyboardAdapter;
    }

    @Override
    public ProfileAdapter getProfileAdapter() {
        return profileAdapter;
    }
}
