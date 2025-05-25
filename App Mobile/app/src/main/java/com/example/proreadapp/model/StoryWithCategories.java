package com.example.proreadapp.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class StoryWithCategories {
    @Embedded
    public Story story;

    @Relation(
            parentColumn = "storyId",
            entityColumn = "categoryId",
            associateBy = @Junction(
                    value = StoryCategoryCrossRef.class,
                    parentColumn = "storyId",
                    entityColumn = "categoryId"
            )
    )
    public List<Category> categories;
}
