package com.example.proreadapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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