package com.example.proreadapp.model;

public class Story {
    private String title, author, info, description;
    private int imageResId;

    public Story(String title, String author, String info, String description, int imageResId) {
        this.title = title;
        this.author = author;
        this.info = info;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getInfo() { return info; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
}
