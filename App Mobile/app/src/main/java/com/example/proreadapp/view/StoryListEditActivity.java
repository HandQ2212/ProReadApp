package com.example.proreadapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.proreadapp.adapter.StoryAdapter;
import com.example.proreadapp.databinding.ActivityStoryListEditBinding;
import com.example.proreadapp.viewmodel.StoryViewModel;

public class StoryListEditActivity extends AppCompatActivity {
    private ActivityStoryListEditBinding binding;
    private StoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoryListEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        viewModel = new ViewModelProvider(this).get(StoryViewModel.class);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        viewModel.getAllStories().observe(this, stories -> {
            if (stories != null && !stories.isEmpty()) {
                StoryAdapter adapter = new StoryAdapter(this, stories, storyId -> {
                    Intent intent = new Intent(this, StoryEditActivity.class);
                    intent.putExtra("storyId", storyId);
                    startActivity(intent);
                });
                binding.recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Không có truyện nào!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.textTitle.setOnClickListener(v -> finish());
    }
}