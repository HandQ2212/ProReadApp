package com.example.proreadapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proreadapp.dao.StoryDao;
import com.example.proreadapp.model.Story;

@Database(entities = {Story.class}, version = 1, exportSchema = false)
public abstract class StoryDatabase extends RoomDatabase {

    private static volatile StoryDatabase INSTANCE;

    public abstract StoryDao storyDao();

    public static StoryDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (StoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    StoryDatabase.class,
                                    "story_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}