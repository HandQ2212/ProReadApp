package com.example.proreadapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proreadapp.model.Story;
import com.example.proreadapp.repository.StoryRepository;

import java.util.List;

public class StoryViewModel extends AndroidViewModel {
    private StoryRepository repository;
    private LiveData<List<Story>> allStories;
    private MutableLiveData<Long> lastInsertedId = new MutableLiveData<>();

    public StoryViewModel(@NonNull Application application) {
        super(application);
        repository = new StoryRepository(application);
        allStories = repository.getAllStories();
    }

    public void insert(Story story) {
        repository.insert(story, id -> {
            lastInsertedId.postValue(id);
        });
    }

    public LiveData<Long> getLastInsertedId() {
        return lastInsertedId;
    }

    public LiveData<List<Story>> getAllStories() {
        return allStories;
    }
}