package com.example.proreadapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.proreadapp.model.Category;
import com.example.proreadapp.model.CategoryWithStories;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.model.StoryCategoryCrossRef;

import java.util.List;

@Dao
public interface StoryDao {

    // CRUD cho Story
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Story story);

    @Update
    void update(Story story);

    @Delete
    void delete(Story story);

    @Query("UPDATE stories SET title = :title, author = :author WHERE id = :id")
    void updateById(String id, String title, String author);

    @Query("DELETE FROM stories WHERE id = :id")
    void deleteById(String id);

    @Query("SELECT * FROM stories WHERE id = :id")
    LiveData<Story> getStoryById(String id);

    @Query("SELECT * FROM stories")
    LiveData<List<Story>> getAllStories();

    @Query("SELECT * FROM stories WHERE title LIKE '%' || :query || '%'")
    LiveData<List<Story>> searchStories(String query);

    @Query("SELECT * FROM stories ORDER BY createdAt DESC")
    LiveData<List<Story>> getNewestStories();

    @Query("SELECT * FROM stories ORDER BY updatedAt DESC")
    LiveData<List<Story>> getRecentlyUpdatedStories();

    @Query("SELECT * FROM stories WHERE status = 'complete'")
    LiveData<List<Story>> getCompleteStories();

    // Chèn hoặc bỏ qua nếu trùng key
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStoryCategoryCrossRef(StoryCategoryCrossRef crossRef);

    // Insert category nếu chưa có (IGNORE để không lỗi khi trùng)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategoryIfNotExists(Category category);

    // Lấy danh sách category theo storyId
    @Transaction
    @Query("SELECT c.* FROM categories c " +
            "INNER JOIN story_category_cross_ref sc ON c.id = sc.categoryId " +
            "WHERE sc.storyId = :storyId")
    LiveData<List<Category>> getCategoriesByStoryId(String storyId);

    // Optional: Insert nhiều category cho 1 story trong 1 transaction (nếu cần)
    @Transaction
    default void insertStoryWithCategories(Story story, List<Category> categories) {
        insert(story);
        for (Category category : categories) {
            insertCategoryIfNotExists(category);
            insertStoryCategoryCrossRef(new StoryCategoryCrossRef(story.getId(), category.getId()));
        }
    }
    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    LiveData<CategoryWithStories> getStoriesByCategoryId(String categoryId);

}
