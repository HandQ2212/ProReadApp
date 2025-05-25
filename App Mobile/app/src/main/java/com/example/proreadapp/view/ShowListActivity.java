package com.example.proreadapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.proreadapp.adapter.StoryAdapter;
import com.example.proreadapp.databinding.ActivityShowListBinding;
import com.example.proreadapp.viewmodel.ShowListViewModel;

import java.util.ArrayList;
import androidx.recyclerview.widget.GridLayoutManager;
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

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));        viewModel = new ViewModelProvider(this).get(ShowListViewModel.class);

        String categoryId = getIntent().getStringExtra("categoryId");
        String title = getIntent().getStringExtra("title");
        ArrayList<String> storyIds = getIntent().getStringArrayListExtra("storyIds");

        if (title != null) {
            binding.textTitle.setText(title);
        }

        if (categoryId != null && !categoryId.isEmpty()) {

            viewModel.getStoriesByCategory(categoryId).observe(this, stories -> {
                if (stories != null && !stories.isEmpty()) {
                    StoryAdapter adapter = new StoryAdapter(this, stories, storyId -> {
                        viewModel.onStoryClicked(this, storyId);
                    });
                    binding.recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(this, "Không có truyện để hiển thị", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (storyIds != null && !storyIds.isEmpty()) {
            binding.showListRoot.setVisibility(View.VISIBLE);
            viewModel.setStoryIds(storyIds);

            viewModel.getStoryList().observe(this, stories -> {
                binding.showListRoot.setVisibility(View.GONE);
                if (stories != null && !stories.isEmpty()) {
                    StoryAdapter adapter = new StoryAdapter(this, stories, storyId -> {
                        viewModel.onStoryClicked(this, storyId);
                    });
                    binding.recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(this, "Không có truyện để hiển thị", Toast.LENGTH_SHORT).show();
                }
            });

            viewModel.getErrorMessage().observe(this, error -> {
                binding.showListRoot.setVisibility(View.GONE);
                if (error != null) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
        }

        binding.textTitle.setOnClickListener(v -> finish());
    }
}
