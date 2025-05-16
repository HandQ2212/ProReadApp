package com.example.proreadapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stories")
public class Story {

    @PrimaryKey
    @NonNull
    private String id;

    private String title;
    private String author;
    private String info;
    private String description;
    private int imageResId;

    // Constructor full (đã có id)
    public Story(@NonNull String id, String title, String author, String info, String description, int imageResId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.info = info;
        this.description = description;
        this.imageResId = imageResId;
    }

    // Getters & setters

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getInfo() {
        return info;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }
}
