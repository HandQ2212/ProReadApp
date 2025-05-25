package com.example.proreadapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proreadapp.model.Category;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.repository.StoryRepository;

import java.util.List;

public class StoryViewModel extends AndroidViewModel {
    private StoryRepository repository;
    private LiveData<List<Story>> allStories;

    public StoryViewModel(@NonNull Application application) {
        super(application);
        repository = new StoryRepository(application);
        allStories = repository.getAllStories();
    }

    public void insertStory(Story story) {
        repository.insertStory(story);
    }

    public void updateStory(Story story) {
        repository.updateStory(story);
    }

    public void deleteStory(Story story) {
        repository.deleteStory(story);
    }

    public LiveData<Story> getStoryById(String storyId) {
        return repository.getStoryById(storyId);
    }

    public LiveData<List<Story>> getAllStories() {
        return allStories;
    }

    public LiveData<List<Story>> searchStories(String query) {
        return repository.searchStories("%" + query + "%");
    }

    public void insertChapter(Chapter chapter) {
        repository.insertChapter(chapter);
    }
    public void updateChapter(Chapter chapter) {
        repository.updateChapter(chapter);
    }


    public LiveData<List<Category>> getCategoriesByStoryId(String storyId) {
        return repository.getCategoriesByStoryId(storyId);
    }


    public LiveData<List<Chapter>> getChaptersByStoryId(String storyId) {
        return repository.getChaptersByStoryId(storyId);
    }

    public void insertStoryWithCategories(String storyId, List<Category> categories) {
        repository.insertStoryWithCategories(storyId, categories);
    }

}