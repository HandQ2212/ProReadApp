package com.example.proreadapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        primaryKeys = {"storyId", "categoryId"},
        tableName = "story_category_cross_ref"
)
public class StoryCategoryCrossRef {
    @NonNull
    public String storyId;

    @NonNull
    public String categoryId;

    public StoryCategoryCrossRef(@NonNull String storyId, @NonNull String categoryId) {
        this.storyId = storyId;
        this.categoryId = categoryId;
    }
}
