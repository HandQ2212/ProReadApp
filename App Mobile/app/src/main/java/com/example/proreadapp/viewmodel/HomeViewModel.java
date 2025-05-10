package com.example.proreadapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.R;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.repository.StoryRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private final StoryRepository repository;
    private final LiveData<List<String>> newestStoryIds;
    private final LiveData<List<String>> recentlyUpdatedStoryIds;
    private final LiveData<List<String>> completeStoryIds;
    private final LiveData<List<String>> favoriteStoryIds;
    private final LiveData<List<String>> mostViewStoryIds;
    private final LiveData<List<String>> trendingStoryIds;

    // Cache cho danh sách stories đã được tải
    private final MutableLiveData<List<Story>> newestStories = new MutableLiveData<>();
    private final MutableLiveData<List<Story>> recentlyUpdatedStories = new MutableLiveData<>();
    private final MutableLiveData<List<Story>> completeStories = new MutableLiveData<>();

    public HomeViewModel(Application application) {
        super(application);
        repository = new StoryRepository(application);

        // Lấy danh sách ID
        newestStoryIds = repository.getNewestStoryIds();
        recentlyUpdatedStoryIds = repository.getRecentlyUpdatedStoryIds();
        completeStoryIds = repository.getCompleteStoryIds();
        favoriteStoryIds = repository.getFavoriteStoryIds();
        mostViewStoryIds = repository.getMostViewStoryIds();
        trendingStoryIds = repository.getTrendingStoryIds();

        // Load data khi có ID
        loadNewestStories();
        loadRecentlyUpdatedStories();
        loadCompleteStories();
    }

    // Phương thức tải dữ liệu từ ID
    private void loadNewestStories() {
        newestStoryIds.observeForever(ids -> {
            if (ids != null && !ids.isEmpty()) {
                repository.getStoriesByIds(ids).observeForever(stories -> {
                    newestStories.setValue(stories);
                });
            }
        });
    }

    private void loadRecentlyUpdatedStories() {
        recentlyUpdatedStoryIds.observeForever(ids -> {
            if (ids != null && !ids.isEmpty()) {
                repository.getStoriesByIds(ids).observeForever(stories -> {
                    recentlyUpdatedStories.setValue(stories);
                });
            }
        });
    }

    private void loadCompleteStories() {
        completeStoryIds.observeForever(ids -> {
            if (ids != null && !ids.isEmpty()) {
                repository.getStoriesByIds(ids).observeForever(stories -> {
                    completeStories.setValue(stories);
                });
            }
        });
    }

    // Getters cho danh sách stories
    public LiveData<List<Story>> getNewestStories() {
        return newestStories;
    }

    public LiveData<List<Story>> getRecentlyUpdatedStories() {
        return recentlyUpdatedStories;
    }

    public LiveData<List<Story>> getCompleteStories() {
        return completeStories;
    }

    // Getters cho danh sách IDs (để truyền qua màn hình khác)
    public LiveData<List<String>> getFavoriteStoryIds() {
        return favoriteStoryIds;
    }

    public LiveData<List<String>> getMostViewStoryIds() {
        return mostViewStoryIds;
    }

    public LiveData<List<String>> getTrendingStoryIds() {
        return trendingStoryIds;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Remove các observer vĩnh viễn nếu cần
    }
}
