package com.example.astghik.searchexample;

import android.app.ActivityManager;

public class User {
    private String imageUrl;
    private String name;
    private String description;
    private String email;
    private String phoneNumber;

    User(String imageUrl, String name, String description, String email, String phoneNumber) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phonNumber) {
        this.phoneNumber = phonNumber;
    }

}
