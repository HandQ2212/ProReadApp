package com.example.proreadapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.proreadapp.dao.ChapterDao;
import com.example.proreadapp.dao.StoryDao;
import com.example.proreadapp.model.Category;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.model.StoryCategoryCrossRef;

@Database(entities ={Story.class, Chapter.class, Category.class, StoryCategoryCrossRef.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract StoryDao storyDao();
    public abstract ChapterDao chapterDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "proread_database")
                    .build();
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database){
            database.execSQL("ALTER TABLE stories ADD COLUMN new_column TEXT DEFAULT ''");
        }
    };


    static final Migration MIGRATION_2_3 = new Migration(2, 3){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database){
            database.execSQL("CREATE TABLE IF NOT EXISTS categories (" +
                    "id TEXT PRIMARY KEY NOT NULL, " +
                    "name TEXT NOT NULL)");
            database.execSQL("CREATE TABLE IF NOT EXISTS story_category_cross_ref (" +
                    "storyId TEXT NOT NULL, " +
                    "categoryId TEXT NOT NULL, " +
                    "PRIMARY KEY(storyId, categoryId), " +
                    "FOREIGN KEY(storyId) REFERENCES stories(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY(categoryId) REFERENCES categories(id) ON DELETE CASCADE)");
        }
    };
}
