package com.example.proreadapp.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithStories {
    @Embedded
    public Category category;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = StoryCategoryCrossRef.class,
                    parentColumn = "categoryId",
                    entityColumn = "storyId"
            )
    )
    public List<Story> stories;
}

