package com.example.proreadapp.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.proreadapp.model.Category;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.model.StoryCategoryCrossRef;
import com.example.proreadapp.dao.StoryDao;
import com.example.proreadapp.dao.ChapterDao;
import com.example.proreadapp.dao.CategoryDao;
import com.example.proreadapp.dao.StoryCategoryDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Story.class, Chapter.class, Category.class, StoryCategoryCrossRef.class}, version = 3, exportSchema = false)
public abstract class StoryDatabase extends RoomDatabase {

    private static volatile StoryDatabase instance;

    public abstract StoryDao storyDao();
    public abstract ChapterDao chapterDao();
    public abstract CategoryDao categoryDao();
    public abstract StoryCategoryDao storyCategoryDao();

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("Migration", "Running migration from 1 to 2");
            database.execSQL("ALTER TABLE stories ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE stories ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE stories ADD COLUMN status TEXT");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("Migration", "Running migration from 2 to 3");
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS categories (" +
                            "id TEXT PRIMARY KEY NOT NULL, " +
                            "name TEXT NOT NULL)"
            );
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS story_category_cross_ref (" +
                            "storyId TEXT NOT NULL, " +
                            "categoryId TEXT NOT NULL, " +
                            "PRIMARY KEY(storyId, categoryId), " +
                            "FOREIGN KEY(storyId) REFERENCES stories(id) ON DELETE CASCADE, " +
                            "FOREIGN KEY(categoryId) REFERENCES categories(id) ON DELETE CASCADE)"
            );
            database.execSQL("CREATE INDEX IF NOT EXISTS index_story_category_cross_ref_storyId ON story_category_cross_ref(storyId)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_story_category_cross_ref_categoryId ON story_category_cross_ref(categoryId)");
        }
    };

    public static synchronized StoryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            StoryDatabase.class,
                            "story_database")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
        }
        return instance;
    }
}
