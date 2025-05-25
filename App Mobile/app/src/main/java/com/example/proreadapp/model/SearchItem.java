package com.example.proreadapp.model;

public class SearchItem {
    private String id;
    private String title;
    private String author;
    private String summary;
    private String thumbnailUrl;


    public SearchItem(String id, String title, String author, String summary, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}