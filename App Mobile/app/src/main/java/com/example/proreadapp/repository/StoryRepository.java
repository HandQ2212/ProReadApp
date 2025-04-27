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
    private StoryDao storyDao;
    private LiveData<List<Story>> allStories;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public StoryRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        storyDao = database.storyDao();
        allStories = storyDao.getAllStories();
    }

    public void insert(Story story, InsertCallback callback) {
        executorService.execute(() -> {
            long id = storyDao.insert(story);
            callback.onInsert(id);
        });
    }

    public LiveData<List<Story>> getAllStories() {
        return allStories;
    }

    public interface InsertCallback {
        void onInsert(long id);
    }
}