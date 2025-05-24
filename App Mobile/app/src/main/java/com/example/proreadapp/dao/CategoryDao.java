package com.example.proreadapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proreadapp.model.Category;
import com.example.proreadapp.model.CategoryWithStories;

@Dao
public interface CategoryDao {

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    LiveData<CategoryWithStories> getCategoryWithStories(String categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Category category);
}
