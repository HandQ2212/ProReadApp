package com.example.proreadapp.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import androidx.annotation.NonNull;

@Entity(
        tableName = "story_category_cross_ref",
        primaryKeys = {"storyId", "categoryId"},
        foreignKeys = {
                @ForeignKey(entity = Story.class, parentColumns = "id", childColumns = "storyId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "categoryId", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("storyId"), @Index("categoryId")}
)
public class StoryCategoryCrossRef {
    @NonNull
    public String storyId;

    @NonNull
    public String categoryId;
}

