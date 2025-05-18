package com.example.proreadapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proreadapp.model.Chapter;

import java.util.List;

@Dao
public interface ChapterDao{
    @Insert
    void insert(Chapter chapter);

    @Query("SELECT * FROM chapters WHERE storyId = :storyId")
    LiveData<List<Chapter>> getChaptersByStoryId(String storyId);

}