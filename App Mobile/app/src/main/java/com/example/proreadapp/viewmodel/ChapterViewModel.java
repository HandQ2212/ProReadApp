package com.example.proreadapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proreadapp.dao.ChapterDao;
import com.example.proreadapp.database.StoryDatabase;
import com.example.proreadapp.model.Chapter;

public class ChapterViewModel extends AndroidViewModel {
    private final ChapterDao chapterDao;
    private int currentChapterId = 1;

    public ChapterViewModel(@NonNull Application application) {
        super(application);
        chapterDao = StoryDatabase.getInstance(application).chapterDao();
    }

    public LiveData<Chapter> getChapterById(int chapterId) {
        currentChapterId = chapterId;
        return chapterDao.getChapterById(chapterId);
    }

    public LiveData<Chapter> getNextChapter() {
        currentChapterId++;
        return getChapterById(currentChapterId);
    }

    public LiveData<Chapter> getChapterByStoryAndChapter(String storyId, int chapterNumber) {
        return chapterDao.getChapterByStoryAndChapter(storyId, chapterNumber);
    }


    public LiveData<Chapter> getPreviousChapter() {
        if (currentChapterId > 1) {
            currentChapterId--;
        }
        return getChapterById(currentChapterId);
    }


    public int getCurrentChapterId() {
        return currentChapterId;
    }
}
