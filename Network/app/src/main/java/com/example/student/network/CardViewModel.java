package com.example.student.network;

public class CardViewModel {

    private String smallImgUrl;
    private String title;
    private String imageUrl;

    public CardViewModel(String smallImgUrl, String title, String imageUrl) {
        this.smallImgUrl = smallImgUrl;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }

    public void setSmallImgUrl(String smallImgUrl) {
        this.smallImgUrl = smallImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
