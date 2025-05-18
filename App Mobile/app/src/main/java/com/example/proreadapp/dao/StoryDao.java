package com.example.proreadapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proreadapp.model.Story;

import java.util.List;

@Dao
public interface StoryDao {
    @Insert
    void insert(Story story);

    @Update
    void update(Story story);

    @Delete
    void delete(Story story);

    @Query("UPDATE stories SET title = :title, author = :author WHERE id = :id")
    void updateById(String id, String title, String author);

    @Query("DELETE FROM stories WHERE id = :id")
    void deleteById(String id);

    @Query("SELECT * FROM stories WHERE id = :id")
    LiveData<Story> getStoryById(String id);

    @Query("SELECT * FROM stories")
    LiveData<List<Story>> getAllStories();

    @Query("SELECT * FROM stories WHERE title LIKE :query")
    LiveData<List<Story>> searchStories(String query);

    @Query("SELECT * FROM stories ORDER BY createdAt DESC")
    LiveData<List<Story>> getNewestStories();

    @Query("SELECT * FROM stories ORDER BY updatedAt DESC")
    LiveData<List<Story>> getRecentlyUpdatedStories();

    @Query("SELECT * FROM stories WHERE status = 'complete'")
    LiveData<List<Story>> getCompleteStories();
}
