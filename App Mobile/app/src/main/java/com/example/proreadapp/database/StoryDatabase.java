package com.example.proreadapp.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.dao.StoryDao;
import com.example.proreadapp.dao.ChapterDao;

@Database(entities = {Story.class, Chapter.class}, version = 2, exportSchema = false)
public abstract class StoryDatabase extends RoomDatabase {

    private static StoryDatabase instance;

    public abstract StoryDao storyDao();
    public abstract ChapterDao chapterDao();

    // Định nghĩa migration từ version 1 lên 2
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d("Migration", "Running migration from 1 to 2");
            database.execSQL("ALTER TABLE stories ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE stories ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE stories ADD COLUMN status TEXT");
        }
    };

    public static synchronized StoryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            StoryDatabase.class,
                            "story_database")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }
}
