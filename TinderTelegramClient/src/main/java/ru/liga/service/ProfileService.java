package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.client.profile.ProfileClient;
import ru.liga.client.profile.TranslatorClient;
import ru.liga.domain.Profile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService implements ProfileClient {

    private final TranslatorClient translatorClient;
    private final ProfileClient profileClient;

    private List<Profile> getTranslatedProfiles(List<Profile> profiles) {
        return profiles.stream()
                .map(translatorClient::getTranslatedProfile)
                .collect(Collectors.toList());
    }

    @Override
    public Profile getUserProfile(Long userId) {
        Profile userProfile = profileClient.getUserProfile(userId);
        return translatorClient.getTranslatedProfile(userProfile);
    }

    @Override
    public List<Profile> getValidProfiles(Long userId) {
        List<Profile> validProfiles = profileClient.getValidProfiles(userId);
        return getTranslatedProfiles(validProfiles);
    }

    @Override
    public List<Profile> getFavorites(Long userId) {
        List<Profile> favorites = profileClient.getFavorites(userId);
        return getTranslatedProfiles(favorites);
    }

    @Override
    public boolean likeProfile(Long userId, Long profileId) {
        return profileClient.likeProfile(userId, profileId);
    }

    @Override
    public void unlikeProfile(Long userId, Long profileId) {
        profileClient.unlikeProfile(userId, profileId);
    }

    @Override
    public String getRelation(Long userId, Long profileId) {
        return profileClient.getRelation(userId, profileId);
    }
}
