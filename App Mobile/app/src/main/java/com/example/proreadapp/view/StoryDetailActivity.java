package com.example.proreadapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.R;
import com.example.proreadapp.databinding.ActivityStoryDetailBinding;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.viewmodel.StoryDetailViewModel;
import android.util.Log;

import java.util.ArrayList;

public class StoryDetailActivity extends AppCompatActivity {

    private ActivityStoryDetailBinding binding;
    private StoryDetailViewModel storyDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        storyDetailViewModel = new ViewModelProvider(this).get(StoryDetailViewModel.class);
        String storyIdStr = getIntent().getStringExtra("storyId");
        if (storyIdStr != null) {
            storyDetailViewModel.loadStoryById(storyIdStr);
        }
        storyDetailViewModel.updateLastReadTime(storyIdStr);


        storyDetailViewModel.getStoryLiveData().observe(this, story -> {
            if (story != null) {
                binding.textTitle.setText(story.getTitle());
                binding.textAuthor.setText(story.getAuthor());
                binding.textInfo.setText(story.getInfo());
                binding.textDescription.setText(story.getDescription());

                if (story.getImageUri() != null) {
                    binding.imageCover.setImageURI(Uri.parse(story.getImageUri()));
                } else {
                    binding.imageCover.setImageResource(R.drawable.ic_image_placeholder);
                }
            }
        });

        binding.btnRead.setOnClickListener(v -> {
            if (storyDetailViewModel.getStoryLiveData().getValue() != null) {
                Intent intent = new Intent(this, ChapterReaderActivity.class);
                intent.putExtra("chapter_id", 1);
                intent.putExtra("story_title", storyDetailViewModel.getStoryLiveData().getValue().getTitle());
                startActivity(intent);
            }
        });

        storyDetailViewModel.getChaptersByStoryId(storyIdStr).observe(this, chapters -> {
            if (chapters != null && !chapters.isEmpty()) {
                ArrayList<Integer> chapterIds = new ArrayList<>();
                Chapter firstChapter = chapters.get(0);
                for (Chapter chapter : chapters) {
                    Log.d("StoryDetailActivity", "Chapter ID: " + chapter.getId()
                            + ", Title: " + chapter.getTitle()
                            + ", CurrentChapter: " + chapter.getCurrentChapter());
                    chapterIds.add(chapter.getId());
                }


                binding.btnRead.setOnClickListener(v -> {
                    Intent intent = new Intent(this, ChapterReaderActivity.class);
                    intent.putIntegerArrayListExtra("chapter_ids", chapterIds);
                    intent.putExtra("chapter_id", firstChapter.getId());
                    intent.putExtra("story_title", storyDetailViewModel.getStoryLiveData().getValue().getTitle());
                    startActivity(intent);
                });
            } else {
                binding.btnRead.setEnabled(false);
            }
        });

    }

}

