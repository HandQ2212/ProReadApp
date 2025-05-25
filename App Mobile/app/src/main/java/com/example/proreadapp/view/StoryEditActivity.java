package com.example.proreadapp.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.proreadapp.adapter.ChapterListAdapter;
import com.example.proreadapp.databinding.ActivityStoryEditBinding;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.viewmodel.StoryEditViewModel;

import java.util.List;

public class StoryEditActivity extends AppCompatActivity {
    private ActivityStoryEditBinding binding;
    private StoryEditViewModel viewModel;
    private String storyId;
    private Story currentStory;
    private List<Chapter> currentChapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoryEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        storyId = getIntent().getStringExtra("storyId");


        viewModel = new ViewModelProvider(this).get(StoryEditViewModel.class);

        setupRecyclerView();
        observeData();

        binding.btnAddChapter.setOnClickListener(v -> {
            if (currentStory != null && currentChapters != null) {
                Integer currentChapterNumber = currentChapters.size() + 1;
                Integer totalChapters = currentChapterNumber;
                viewModel.addNewChapter(this, currentStory.getId(), currentChapterNumber, totalChapters);
            } else {
                Toast.makeText(this, "Dữ liệu truyện chưa sẵn sàng", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSave.setOnClickListener(v -> {
            if (currentStory != null) {
                boolean isCompleted = binding.switchCompleted.isChecked();
                viewModel.updateStoryCompletionStatus(currentStory, isCompleted);
                Toast.makeText(this, "Đã lưu thay đổi", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Dữ liệu truyện chưa sẵn sàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        binding.recyclerChapters.setLayoutManager(new LinearLayoutManager(this));
    }

    private void observeData() {
        viewModel.getStoryWithChapters(storyId).observe(this, storyWithChapters -> {
            if (storyWithChapters != null) {
                currentStory = storyWithChapters.story;
                currentChapters = storyWithChapters.chapters;
                Log.d("DEBUG", "Story ID: " + currentStory.getId());
                Log.d("DEBUG", "Story Title: " + currentStory.getTitle());

                for (Chapter chapter : currentChapters) {
                    Log.d("DEBUG", "Chapter: " + chapter.getTitle());
                }

                binding.textStoryTitle.setText(currentStory.getTitle());
                binding.switchCompleted.setChecked(currentStory.getStatus());

                ChapterListAdapter adapter = new ChapterListAdapter(chapter -> {
                    viewModel.editChapter(this, chapter);
                });
                adapter.setChapterList(currentChapters);
                Log.d("StoryEditActivity", "Chapters count = " + currentChapters.size());
                for (Chapter ch : currentChapters) {
                    Log.d("StoryEditActivity", "Chapter: " + ch.getTitle());
                }

                binding.recyclerChapters.setAdapter(adapter);
            }
        });
    }
}
