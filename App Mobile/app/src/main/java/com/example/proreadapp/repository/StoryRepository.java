package com.example.proreadapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.proreadapp.database.StoryDatabase;
import com.example.proreadapp.model.Category;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.model.StoryCategoryCrossRef;
import com.example.proreadapp.dao.StoryDao;
import com.example.proreadapp.dao.ChapterDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StoryRepository {
    private final StoryDao storyDao;
    private final ChapterDao chapterDao;
    private final LiveData<List<Story>> allStories;
    private final ExecutorService executorService;

    public StoryRepository(Application application) {
        StoryDatabase database = StoryDatabase.getInstance(application);
        storyDao = database.storyDao();
        chapterDao = database.chapterDao();
        allStories = storyDao.getAllStories();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertStory(final Story story) {
        executorService.execute(() -> storyDao.insert(story));
    }

    public void updateStory(final Story story) {
        executorService.execute(() -> storyDao.update(story));
    }

    public void deleteStory(final Story story) {
        executorService.execute(() -> storyDao.delete(story));
    }

    public LiveData<Story> getStoryById(String storyId) {
        return storyDao.getStoryById(storyId);
    }

    public LiveData<List<Story>> getAllStories() {
        return allStories;
    }

    public LiveData<List<Story>> searchStories(String query) {
        return storyDao.searchStories(query);
    }

    public void insertChapter(final Chapter chapter) {
        executorService.execute(() -> chapterDao.insert(chapter));
    }

    public LiveData<List<Chapter>> getChaptersByStoryId(String storyId) {
        return chapterDao.getChaptersByStoryId(storyId);
    }

    public LiveData<List<Story>> getNewestStories() {
        return storyDao.getNewestStories();
    }

    public LiveData<List<Story>> getRecentlyUpdatedStories() {
        return storyDao.getRecentlyUpdatedStories();
    }

    public LiveData<List<Category>> getCategoriesByStoryId(String storyId) {
        return storyDao.getCategoriesByStoryId(storyId);
    }
    public LiveData<List<Story>> getStoriesByCategoryId(String categoryId) {
        return Transformations.map(storyDao.getStoriesByCategoryId(categoryId), input -> {
            if (input != null) return input.stories;
            return new ArrayList<>();
        });
    }


    public LiveData<List<Story>> getCompleteStories() {
        return storyDao.getCompleteStories();
    }

    public void insertStoryWithCategories(String storyId, List<Category> categories) {
        StoryDatabase.databaseWriteExecutor.execute(() -> {
            for (Category category : categories) {
                storyDao.insertCategoryIfNotExists(category); // phòng trường hợp category mới
                storyDao.insertStoryCategoryCrossRef(new StoryCategoryCrossRef(storyId, category.getId()));
            }
        });
    }

}
