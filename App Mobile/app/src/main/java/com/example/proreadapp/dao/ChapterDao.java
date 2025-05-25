package com.example.proreadapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proreadapp.model.Chapter;

import java.util.List;

@Dao
public interface ChapterDao{
    @Insert
    void insert(Chapter chapter);

    @Query("SELECT * FROM chapters WHERE storyId = :storyId")
    LiveData<List<Chapter>> getChaptersByStoryId(String storyId);

    @Query("SELECT * FROM chapters WHERE id = :chapterId")
    LiveData<Chapter> getChapterById(int chapterId);

    @Query("SELECT * FROM chapters WHERE storyId = :storyId AND currentChapter = :chapterNumber LIMIT 1")
    LiveData<Chapter> getChapterByStoryAndChapter(String storyId, int chapterNumber);

    @Update
    void update(Chapter chapter);

}