package com.instigatemobile.grapes.models;

public class FileModel {

    private String mSize;
    private String mIcon;
    private String mName;
    private String mPath;
    private String mDate;
    private String mExtension;

    public FileModel(String size, String icon, String name, String path, String date, String extension) {
        mSize = size;
        mIcon = icon;
        mName = name;
        mPath = path;
        mDate = date;
        mExtension = extension;
    }

    public String getSize() {
        return mSize;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        this.mIcon = icon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getDate() {
        return mDate;
    }

    public String getExtension() {
        return mExtension;
    }
}
