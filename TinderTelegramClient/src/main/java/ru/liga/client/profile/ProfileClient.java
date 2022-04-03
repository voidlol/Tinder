package ru.liga.client.profile;

import ru.liga.domain.Profile;

import java.util.List;

public interface ProfileClient {
    Profile getUserProfile(Long userId);

    List<Profile> getValidProfiles(Long userId);

    boolean likeProfile(Long userId, Long profileId);

    List<Profile> getFavorites(Long userId);

    void unlikeProfile(Long userId, Long profileId);

    String getRelation(Long userId, Long profileId);
}
