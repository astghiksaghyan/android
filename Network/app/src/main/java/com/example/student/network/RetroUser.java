package com.example.student.network;

import com.google.gson.annotations.SerializedName;

public class RetroUser {

    public static final int MALE_TYPE=0;
    public static final int FEMALE_TYPE=2;


    @SerializedName("gender")
    private String gender;
    @SerializedName("name")
    private String name;
    @SerializedName("latitude")
    private float latitude;
    @SerializedName("longitude")
    private float longitude;
    @SerializedName("email")
    private String email;
    @SerializedName("cell")
    private String cell;
    @SerializedName("largePicture")
    private String largePicture;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("type")
    private int type;

    public RetroUser(String gender, String name, float latitude, float longitude, String email, String cell, String largePicture, String thumbnail, int type) {
        this.gender = gender;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.cell = cell;
        this.largePicture = largePicture;
        this.thumbnail = thumbnail;
        this.type = type;
    }

    public static int getMaleType() {
        return MALE_TYPE;
    }

    public static int getFemaleType() {
        return FEMALE_TYPE;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getLargePicture() {
        return largePicture;
    }

    public void setLargePicture(String largPicture) {
        this.largePicture = largPicture;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
