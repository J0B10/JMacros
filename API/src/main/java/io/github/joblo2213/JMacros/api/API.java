package io.github.joblo2213.JMacros.api;

import io.github.joblo2213.JMacros.api.adapters.KeyboardAdapter;
import io.github.joblo2213.JMacros.api.adapters.MouseAdapter;
import io.github.joblo2213.JMacros.api.adapters.ProfileAdapter;

public interface API {
    MouseAdapter getMouseAdapter();

    KeyboardAdapter getKeyboardAdapter();

    ProfileAdapter getProfileAdapter();
}
