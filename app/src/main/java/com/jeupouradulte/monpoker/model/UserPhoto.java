package com.jeupouradulte.monpoker.model;

import androidx.annotation.Nullable;

public class UserPhoto {

    private String urlImage;
    @Nullable
    private String urlPicture;

    public UserPhoto() { }

    public UserPhoto(String uid, String username, @Nullable String urlPicture) {

        this.urlImage = username;
        this.urlPicture = urlPicture;
    }

    // --- GETTERS ---
    public String getUsername() { return urlImage; }
    @Nullable
    public String getUrlPicture() { return urlPicture; }

    // --- SETTERS ---
    public void setUsername(String username) { this.urlPicture = username; }
    public void setUrlPicture(@Nullable String urlPicture) { this.urlPicture = urlPicture; }
}
