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
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.model.Story;

@Database(entities ={Story.class, Chapter.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase instance;

    // Liên kết DAO
    public abstract StoryDao storyDao();
    public abstract ChapterDao chapterDao();

    // Singleton để đảm bảo chỉ có một instance của cơ sở dữ liệu
    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "proread_database")
                    .addMigrations(MIGRATION_1_2)
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
}