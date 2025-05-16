package com.example.proreadapp.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.R;
import com.example.proreadapp.databinding.ActivityStoryDetailBinding;
import com.example.proreadapp.viewmodel.StoryDetailViewModel;

public class StoryDetailActivity extends AppCompatActivity {

    private ActivityStoryDetailBinding binding;
    private StoryDetailViewModel storyDetailViewModel;

    private String id, title, author, info, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.dark_gray));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decor = window.getDecorView();
            }
        }

        setupViewBinding();
        setupViewModel();
        fetchIntentData();
        setupObservers();
        setupReadButtonListener();
    }

    private void setupViewBinding() {
        binding = ActivityStoryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setupViewModel() {
        storyDetailViewModel = new ViewModelProvider(this).get(StoryDetailViewModel.class);
    }

    private void fetchIntentData() {
        Intent intent = getIntent();

        id = intent.getStringExtra("id");  // Sửa: lấy id đầy đủ từ Intent
        title = intent.getStringExtra("title");
        author = intent.getStringExtra("author");
        info = intent.getStringExtra("info");
        description = intent.getStringExtra("description");
        int imageResId = intent.getIntExtra("imageResId", -1);

        storyDetailViewModel.setStoryData(id, title, author, info, description, imageResId);
    }

    private void setupObservers() {
        storyDetailViewModel.getStoryLiveData().observe(this, story -> {
            if (story != null) {
                binding.textTitle.setText(story.getTitle());
                binding.textAuthor.setText(story.getAuthor());
                binding.textInfo.setText(story.getInfo());
                binding.textDescription.setText(story.getDescription());
                if (story.getImageResId() != -1) {
                    binding.imageCover.setImageResource(story.getImageResId());
                } else {
                    binding.imageCover.setImageResource(R.drawable.ic_image_placeholder);
                }
            }
        });
    }

    private void setupReadButtonListener() {
        binding.btnRead.setOnClickListener(v -> {
            if (storyDetailViewModel.getStoryLiveData().getValue() != null) {
                Intent intent = new Intent(this, ChapterReaderActivity.class);
                intent.putExtra("chapter_id", 1);
                intent.putExtra("story_title", storyDetailViewModel.getStoryLiveData().getValue().getTitle());
                startActivity(intent);
            }
        });
    }
}
