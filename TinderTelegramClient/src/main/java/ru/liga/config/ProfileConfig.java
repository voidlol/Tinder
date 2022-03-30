package ru.liga.config;

import lombok.Getter;

public class ProfileConfig {

    @Getter
    private final String profileUrl;
    private final String likeUrl;
    private final String unlikeUrl;

    public ProfileConfig(String serverUrl, String apiUrl, String profileUrl, String likeUrl, String unlikeUrl) {
        this.profileUrl = serverUrl + apiUrl + profileUrl;
        this.likeUrl = likeUrl;
        this.unlikeUrl = unlikeUrl;
    }

    public String getLikeUrl() {
        return profileUrl + likeUrl;
    }

    public String getUnlikeUrl() {
        return profileUrl + unlikeUrl;
    }
}
