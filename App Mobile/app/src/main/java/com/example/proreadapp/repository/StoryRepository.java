package com.example.proreadapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.proreadapp.dao.StoryDao;
import com.example.proreadapp.database.AppDatabase;
import com.example.proreadapp.model.Story;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StoryRepository {
    private final StoryDao storyDao;

    public StoryRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        storyDao = database.storyDao();
    }
    public Story getStoryById(String storyId) {
        return storyDao.getStoryById(storyId);
    }

}