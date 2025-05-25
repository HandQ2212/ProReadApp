package com.example.proreadapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proreadapp.database.StoryDatabase;
import com.example.proreadapp.model.Category;
import com.example.proreadapp.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private final CategoryRepository repository;
    private final LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(StoryDatabase.getInstance(application).categoryDao());
        allCategories = repository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insertCategory(Category category) {
        repository.insertCategory(category.getName());
    }

    public void insertDefaultCategories() {
        repository.insertDefaultCategories();
    }

    public LiveData<List<Category>> getCategories() {
        return allCategories;
    }
}
