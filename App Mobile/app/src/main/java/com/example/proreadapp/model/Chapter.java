package com.example.proreadapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chapters")
public class Chapter {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String storyId;
    private String title;
    private String content;
    private int currentChapter;
    private int totalChapters;

    public Chapter(String title, String content, int currentChapter, int totalChapters, String storyId) {
        this.title = title;
        this.content = content;
        this.currentChapter = currentChapter;
        this.totalChapters = totalChapters;
        this.storyId = storyId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getStoryId() { return storyId; }
    public void setStoryId(String storyId) { this.storyId = storyId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getCurrentChapter() { return currentChapter; }
    public int getTotalChapters() { return totalChapters; }
}