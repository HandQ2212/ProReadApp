package com.example.proreadapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proreadapp.model.Story;

import java.util.List;

@Dao
public interface StoryDao{
    @Insert
    long insert(Story story);

    @Query("SELECT * FROM stories")
    LiveData<List<Story>> getAllStories();
}