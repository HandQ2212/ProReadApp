package com.example.proreadapp.viewmodel;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.view.ChapterAddEditActivity;

public class ChapterEditLauncher {
    public static void launchEditor(AppCompatActivity activity, Chapter chapter) {
        if (activity == null || chapter == null) return;

        Intent intent = new Intent(activity, ChapterAddEditActivity.class);
        intent.putExtra(ChapterAddEditActivity.EXTRA_CHAPTER_ID, chapter.getId());
        intent.putExtra(ChapterAddEditActivity.EXTRA_STORY_ID, chapter.getStoryId());
        activity.startActivity(intent);
    }
}
