package com.example.proreadapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stories")
public class Story{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String author;
    private String info;
    private String description;
    private int imageResId;

    public Story(String title, String author, String info, String description, int imageResId){
        this.title = title;
        this.author = author;
        this.info = info;
        this.description = description;
        this.imageResId = imageResId;
    }

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }
    public String getTitle(){ return title; }
    public String getAuthor(){ return author; }
    public String getInfo(){ return info; }
    public String getDescription(){ return description; }
    public int getImageResId(){ return imageResId; }
}