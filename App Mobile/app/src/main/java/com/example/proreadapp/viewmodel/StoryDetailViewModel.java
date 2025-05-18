package com.example.proreadapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.model.Story;
import com.example.proreadapp.repository.StoryRepository;
public class StoryDetailViewModel extends AndroidViewModel {
    private final StoryRepository repository;
    private final MutableLiveData<Story> storyLiveData = new MutableLiveData<>();

    public StoryDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new StoryRepository(application);
    }

    public void loadStoryById(String id) {
        repository.getStoryById(id).observeForever(story -> {
            storyLiveData.setValue(story);
        });
    }

    public LiveData<Story> getStoryLiveData() {
        return storyLiveData;
    }
}
