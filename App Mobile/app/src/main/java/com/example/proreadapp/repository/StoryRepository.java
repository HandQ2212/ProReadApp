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

    // Các phương thức lấy danh sách ID
    public LiveData<List<String>> getNewestStoryIds() {
        return storyDao.getNewestStoryIds();
    }

    public LiveData<List<String>> getRecentlyUpdatedStoryIds() {
        return storyDao.getRecentlyUpdatedStoryIds();
    }

    public LiveData<List<String>> getCompleteStoryIds() {
        return storyDao.getCompleteStoryIds();
    }

    public LiveData<List<String>> getFavoriteStoryIds() {
        return storyDao.getFavoriteStoryIds();
    }

    public LiveData<List<String>> getMostViewStoryIds() {
        return storyDao.getMostViewStoryIds();
    }

    public LiveData<List<String>> getTrendingStoryIds() {
        return storyDao.getTrendingStoryIds();
    }

    // Phương thức lấy story theo ID
    public LiveData<Story> getStoryById(String storyId) {
        return storyDao.getStoryById(storyId);
    }

    // Phương thức lấy nhiều story theo danh sách ID
    public LiveData<List<Story>> getStoriesByIds(List<String> storyIds) {
        return storyDao.getStoriesByIds(storyIds);
    }
}