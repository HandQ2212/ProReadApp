package com.example.proreadapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.databinding.ActivityStoryDetailBinding;
import com.example.proreadapp.viewmodel.StoryDetailViewModel;

public class StoryDetailActivity extends AppCompatActivity {

    private ActivityStoryDetailBinding binding;
    private StoryDetailViewModel storyDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sử dụng View Binding
        binding = ActivityStoryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo ViewModel thông qua ViewModelProvider
        storyDetailViewModel = new ViewModelProvider(this).get(StoryDetailViewModel.class);

        // Lấy dữ liệu từ Intent
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String info = getIntent().getStringExtra("info");
        String description = getIntent().getStringExtra("description");
        int imageResId = getIntent().getIntExtra("imageResId", -1);

        // Gán dữ liệu vào ViewModel
        storyDetailViewModel.setStoryData(title, author, info, description, imageResId);

        // Quan sát LiveData từ ViewModel và cập nhật giao diện
        storyDetailViewModel.getStoryLiveData().observe(this, story -> {
            if (story != null) {
                binding.textTitle.setText(story.getTitle());
                binding.textAuthor.setText(story.getAuthor());
                binding.textInfo.setText(story.getInfo());
                binding.textDescription.setText(story.getDescription());
                if (story.getImageResId() != -1) {
                    binding.imageCover.setImageResource(story.getImageResId());
                }
            }
        });

        binding.btnRead.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChapterReaderActivity.class);
            intent.putExtra("chapter_id", 1); // Hoặc truyền theo kiểu bạn muốn (ví dụ chapter đầu tiên)
            intent.putExtra("story_title", storyDetailViewModel.getStoryLiveData().getValue().getTitle());
            startActivity(intent);
        });

    }
}