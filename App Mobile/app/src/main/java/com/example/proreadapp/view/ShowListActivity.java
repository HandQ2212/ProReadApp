package com.example.proreadapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.proreadapp.adapter.StoryAdapter;
import com.example.proreadapp.databinding.ActivityShowListBinding;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.viewmodel.ShowListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShowListActivity extends AppCompatActivity {
    private ActivityShowListBinding binding;
    private ShowListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityShowListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up RecyclerView with LinearLayoutManager
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ShowListViewModel.class);

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        binding.textTitle.setText(title);
        ArrayList<String> storyIds = getIntent().getStringArrayListExtra("storyIds");

        if (storyIds != null && !storyIds.isEmpty()) {
            // Show loading state
            binding.showListRoot.setVisibility(View.VISIBLE);

            // Pass story IDs to ViewModel
            viewModel.setStoryIds(storyIds);

            // Observe story list changes
            viewModel.getStoryList().observe(this, stories -> {
                binding.showListRoot.setVisibility(View.GONE);

                if (stories != null && !stories.isEmpty()) {
                    // Create adapter and set to RecyclerView with story IDs
                    StoryAdapter adapter = new StoryAdapter(this, stories, storyId -> {
                        // Handle story click by ID
                        viewModel.onStoryClicked(this, storyId);
                    });
                    binding.recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(this, "Không có truyện để hiển thị", Toast.LENGTH_SHORT).show();
                }
            });

            // Observe error states
            viewModel.getErrorMessage().observe(this, errorMsg -> {
                binding.showListRoot.setVisibility(View.GONE);
                if (errorMsg != null && !errorMsg.isEmpty()) {
                    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Không có ID truyện để hiển thị", Toast.LENGTH_SHORT).show();
        }

        // Set up back button
        binding.textTitle.setOnClickListener(v -> finish());
    }
}