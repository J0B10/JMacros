package io.github.joblo2213.JMacros.api.adapters;

import io.github.joblo2213.JMacros.api.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileAdapter {
    List<Profile> getProfiles();

    Optional<Profile> getProfile(int id);

    Optional<Profile> getCurrentProfile();

    boolean setCurrentProfile(int id);

    void nextProfile();

    void previousProfile();
}
