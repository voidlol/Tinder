package ru.liga.config;

import lombok.Getter;

public class ProfileConfig {

    @Getter
    private final String profileUrl;
    private final String likeUrl;
    private final String unlikeUrl;
    private final String weLike;

    public ProfileConfig(String serverUrl, String apiUrl, String profileUrl, String likeUrl, String unlikeUrl, String weLike) {
        this.profileUrl = serverUrl + apiUrl + profileUrl;
        this.likeUrl = likeUrl;
        this.unlikeUrl = unlikeUrl;
        this.weLike = weLike;
    }

    public String getLikeUrl() {
        return profileUrl + likeUrl;
    }

    public String getUnlikeUrl() {
        return profileUrl + unlikeUrl;
    }

    public String getWeLikeUrl() {
        return profileUrl + weLike;
    }

    public String getUsLikeUrl() {
        return profileUrl + "/usLike";
    }

    public String getFavoritesUrl() {
        return profileUrl + "/favorites";
    }
}
