package com.example.proreadapp.repository;

import com.example.proreadapp.dao.CategoryDao;
import com.example.proreadapp.model.Category;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class CategoryRepository {

    private final CategoryDao categoryDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CategoryRepository(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public LiveData<List<Category>> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public void insertCategory(String name) {
        executor.execute(() -> {
            Category category = new Category(UUID.randomUUID().toString(), name);
            categoryDao.insert(category);
        });
    }

    public void insertDefaultCategories() {
        insertCategory("Ngôn Tình");
        insertCategory("Ngược");
        insertCategory("Huyền Huyễn");
        insertCategory("Khoa học");
        insertCategory("Xuyên Không");
        insertCategory("Niên Đại");
    }
}
