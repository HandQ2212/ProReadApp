package com.example.proreadapp.model;

import androidx.room.Embedded;
import androidx.room.Relation;


import java.util.List;

public class StoryWithChapters {
    @Embedded
    public Story story;

    @Relation(parentColumn = "id", entityColumn = "storyId")
    public List<Chapter> chapters;
}
