package com.example.proreadapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proreadapp.model.Category;
import com.example.proreadapp.model.CategoryWithStories;

import java.util.List;

@Dao
public interface CategoryDao {

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    LiveData<CategoryWithStories> getCategoryWithStories(String categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM categories c INNER JOIN story_category_cross_ref sc ON c.id = sc.categoryId WHERE sc.storyId = :storyId")
    LiveData<List<Category>> getCategoriesByStoryId(String storyId);

}
