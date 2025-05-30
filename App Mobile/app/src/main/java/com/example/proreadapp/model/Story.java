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
    private Integer imageResId;
    private String imageUri;

    private long createdAt;
    private long updatedAt;
    private long lastReadAt;
    private boolean status;

    public Story(@NonNull String id, String title, String author, String info, String description, Integer imageResId, String imageUri) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.info = info;
        this.description = description;
        this.imageResId = imageResId;
        this.imageUri = imageUri;
    }


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

    public Integer getImageResId() {
        return imageResId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public long getLastReadAt() {
        return lastReadAt;
    }

    public void setLastReadAt(long lastReadAt) {
        this.lastReadAt = lastReadAt;
    }
}
