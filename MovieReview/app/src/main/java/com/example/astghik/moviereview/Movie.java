package com.example.astghik.moviereview;

public class Movie {
    private String title;
    private int imageId;
    private String description;
    private int rating;
    private boolean heart;
    private String wikiUrl;

    public Movie(String title, int imageId, String description, int rating, boolean heart, String wikiUrl) {
        this.title = title;
        this.imageId = imageId;
        this.description = description;
        this.rating = rating;
        this.heart = heart;
        this.wikiUrl = wikiUrl;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public String getWikiUrl() {

        return wikiUrl;
    }

    public void setRating(int rating) {
        this.rating = rating;

    }

    public boolean isHeart() {
        return heart;
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }
}
