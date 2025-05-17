package com.example.proreadapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.repository.StoryRepository;

import org.jspecify.annotations.NonNull;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private final StoryRepository repository;

    public HomeViewModelFactory(StoryRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

