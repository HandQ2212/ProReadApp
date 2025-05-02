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

public class StoryDetailActivity extends AppCompatActivity{

    private ActivityStoryDetailBinding binding;
    private StoryDetailViewModel storyDetailViewModel;
    String title, author, info, description;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.dark_gray));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decor = window.getDecorView();
                decor.setSystemUiVisibility(0); // Không cho icon đổi màu
            }
        }

        setupViewBinding();
        setupViewModel();
        fetchIntentData();
        setupObservers();
        setupReadButtonListener();
    }

    private void setupViewBinding(){
        binding = ActivityStoryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setupViewModel(){
        storyDetailViewModel = new ViewModelProvider(this).get(StoryDetailViewModel.class);
    }

    private void fetchIntentData(){
        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        info = getIntent().getStringExtra("info");
        description = getIntent().getStringExtra("description");
        int imageResId = getIntent().getIntExtra("imageResId", -1);

        storyDetailViewModel.setStoryData(title, author, info, description, imageResId);
    }

    private void setupObservers(){
        storyDetailViewModel.getStoryLiveData().observe(this, story ->{
            if (story != null){
                binding.textTitle.setText(story.getTitle());
                binding.textAuthor.setText(story.getAuthor());
                binding.textInfo.setText(story.getInfo());
                binding.textDescription.setText(story.getDescription());
                if (story.getImageResId() != -1){
                    binding.imageCover.setImageResource(story.getImageResId());
                }
            }
        });
    }

    private void setupReadButtonListener(){
        binding.btnRead.setOnClickListener(v ->{
            Intent intent = new Intent(this, ChapterReaderActivity.class);
            intent.putExtra("chapter_id", 1);
            intent.putExtra("story_title", storyDetailViewModel.getStoryLiveData().getValue().getTitle());
            startActivity(intent);
        });
    }
}