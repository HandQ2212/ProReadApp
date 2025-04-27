package com.example.proreadapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.proreadapp.dao.ChapterDao;
import com.example.proreadapp.database.AppDatabase;
import com.example.proreadapp.model.Chapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChapterRepository{
    private ChapterDao chapterDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ChapterRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        chapterDao = database.chapterDao();
    }

    public void insert(Chapter chapter){
        executorService.execute(() -> chapterDao.insert(chapter));
    }

    public LiveData<List<Chapter>> getChaptersByStoryId(int storyId){
        return chapterDao.getChaptersByStoryId(storyId);
    }
}