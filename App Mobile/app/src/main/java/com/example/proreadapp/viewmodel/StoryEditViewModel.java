package com.example.proreadapp.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proreadapp.dao.StoryDao;
import com.example.proreadapp.database.StoryDatabase;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.model.StoryWithChapters;
import com.example.proreadapp.view.ChapterAddEditActivity;

public class StoryEditViewModel extends AndroidViewModel {
    private StoryDao storyDao;
    private LiveData<StoryWithChapters> storyWithChapters;

    public StoryEditViewModel(@NonNull Application application) {
        super(application);
        storyDao = StoryDatabase.getInstance(application).storyDao();
    }

    public LiveData<StoryWithChapters> getStoryWithChapters(String storyId) {
        storyWithChapters = storyDao.getStoryWithChapters(storyId);
        return storyWithChapters;
    }

    public void updateStoryCompletionStatus(Story story, boolean isCompleted) {
        new Thread(() -> {
            story.setStatus(isCompleted);
            storyDao.updateStory(story);
        }).start();
    }

    public void addNewChapter(AppCompatActivity activity, String storyId, Integer currentChapter, Integer totalChapters) {
        new Thread(() -> {
            Chapter newChapter = new Chapter(
                    "Chương mới",
                    "",
                    currentChapter,
                    totalChapters,
                    storyId
            );

            long newId = storyDao.insertChapter(newChapter);

            activity.runOnUiThread(() -> {
                Intent intent = new Intent(activity, ChapterAddEditActivity.class);
                intent.putExtra("chapterId", (int) newId);
                activity.startActivity(intent);
            });
        }).start();
    }



    public void editChapter(AppCompatActivity activity, Chapter chapter) {
        ChapterEditLauncher.launchEditor(activity, chapter);
    }

}
