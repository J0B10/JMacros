package io.github.joblo2213.JMacros.core.adapters;

import io.github.joblo2213.JMacros.api.Profile;
import io.github.joblo2213.JMacros.api.adapters.ProfileAdapter;
import io.github.joblo2213.JMacros.core.JMacros;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ProfileDataAdapter implements ProfileAdapter {

    private final JMacros jMacros;

    public ProfileDataAdapter(JMacros jMacros) {
        this.jMacros = jMacros;
    }

    @Override
    public List<Profile> getProfiles() {
        return Collections.unmodifiableList(jMacros.getConfigManager().getProfiles());
    }

    @Override
    public Optional<Profile> getProfile(int id) {
        return jMacros.getConfigManager().getProfile(id).map(Function.identity());
    }

    @Override
    public Optional<Profile> getCurrentProfile() {
        return jMacros.getCurrentProfile();
    }

    @Override
    public boolean setCurrentProfile(int id) {
        Optional<Profile> profile = getProfile(id);
        if (profile.isEmpty()) return false;
        jMacros.setCurrentProfile(profile.get());
        return true;
    }

    @Override
    public void nextProfile() {
        //TODO allow cycling through profiles
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public void previousProfile() {
        //TODO allow cycling through profiles
        throw new UnsupportedOperationException("todo");
    }
}
