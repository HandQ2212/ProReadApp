package com.example.proreadapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proreadapp.model.Story;

import java.util.List;

@Dao
public interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Story story);

    @Update
    void update(Story story);

    @Delete
    void delete(Story story);

    @Query("SELECT * FROM stories WHERE id = :storyId")
    Story getStoryById(String storyId);

    @Query("SELECT * FROM stories")
    LiveData<List<Story>> getAllStories();

    @Query("SELECT * FROM stories WHERE title LIKE :searchQuery OR author LIKE :searchQuery")
    LiveData<List<Story>> searchStories(String searchQuery);

    @Query("SELECT * FROM stories ORDER BY createdAt DESC")
    LiveData<List<Story>> getNewestStories();

    @Query("SELECT * FROM stories ORDER BY updatedAt DESC")
    LiveData<List<Story>> getRecentlyUpdatedStories();

    @Query("SELECT * FROM stories WHERE status = 'complete'")
    LiveData<List<Story>> getCompleteStories();
}