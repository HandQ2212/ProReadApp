package com.example.proreadapp.model;

public class Chapter {
    private String title;
    private String content;
    private int currentChapter;
    private int totalChapters;

    public Chapter(String title, String content, int currentChapter, int totalChapters) {
        this.title = title;
        this.content = content;
        this.currentChapter = currentChapter;
        this.totalChapters = totalChapters;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getCurrentChapter() { return currentChapter; }
    public int getTotalChapters() { return totalChapters; }
}
