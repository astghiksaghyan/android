package com.example.astghik.searchexample;

public class User {
    String imageUrl;
    String name;

    public User(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
