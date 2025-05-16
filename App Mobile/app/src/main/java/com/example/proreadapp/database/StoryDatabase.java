package com.example.proreadapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.dao.StoryDao;
import com.example.proreadapp.dao.ChapterDao;

@Database(entities = {Story.class, Chapter.class}, version = 1, exportSchema = false)
public abstract class StoryDatabase extends RoomDatabase {

    private static StoryDatabase instance;

    public abstract StoryDao storyDao();
    public abstract ChapterDao chapterDao();

    public static synchronized StoryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            StoryDatabase.class,
                            "story_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}