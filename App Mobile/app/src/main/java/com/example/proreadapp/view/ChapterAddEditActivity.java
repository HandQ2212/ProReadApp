package com.example.proreadapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.databinding.ActivityChapterAddEditBinding;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.viewmodel.StoryViewModel;

public class ChapterAddEditActivity extends AppCompatActivity {

    public static final String EXTRA_CHAPTER_ID = "com.example.storyapp.EXTRA_CHAPTER_ID";
    public static final String EXTRA_STORY_ID = "com.example.storyapp.EXTRA_STORY_ID";

    private ActivityChapterAddEditBinding binding;
    private StoryViewModel storyViewModel;

    private int chapterId = -1;
    private String storyId;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityChapterAddEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storyViewModel = new ViewModelProvider(this).get(StoryViewModel.class);

        Intent intent = getIntent();
        if (intent != null) {
            storyId = intent.getStringExtra(EXTRA_STORY_ID);

            if (intent.hasExtra(EXTRA_CHAPTER_ID)) {
                chapterId = intent.getIntExtra(EXTRA_CHAPTER_ID, -1);
                isEditMode = true;
                loadChapterData();
            } else {
                isEditMode = false;
                int defaultTotalChapters = 1;
                int defaultCurrentChapter = 1;

                storyViewModel.getChaptersByStoryId(storyId).observe(this, chapters -> {
                    if (chapters != null && !chapters.isEmpty()) {
                        int maxChapterNumber = 0;
                        int maxTotalChapters = 0;

                        for (Chapter chapter : chapters) {
                            if (chapter.getCurrentChapter() > maxChapterNumber) {
                                maxChapterNumber = chapter.getCurrentChapter();
                            }
                            if (chapter.getTotalChapters() > maxTotalChapters) {
                                maxTotalChapters = chapter.getTotalChapters();
                            }
                        }

                        binding.editCurrentChapter.setText(String.valueOf(maxChapterNumber + 1));
                        binding.editTotalChapters.setText(String.valueOf(Math.max(maxTotalChapters, maxChapterNumber + 1)));
                    } else {
                        binding.editCurrentChapter.setText(String.valueOf(defaultCurrentChapter));
                        binding.editTotalChapters.setText(String.valueOf(defaultTotalChapters));
                    }
                });
            }
        }

        binding.btnSaveChapter.setOnClickListener(v -> {
            saveChapter();
            finish();
        });

        binding.btnNextChapter.setOnClickListener(v -> {
            saveChapter();
            clearFieldsForNextChapter();
        });
    }

    private void loadChapterData() {
        storyViewModel.getChaptersByStoryId(storyId).observe(this, chapters -> {
            if (chapters != null) {
                for (Chapter chapter : chapters) {
                    if (chapter.getId() == chapterId) {
                        binding.editChapterTitle.setText(chapter.getTitle());
                        binding.editCurrentChapter.setText(String.valueOf(chapter.getCurrentChapter()));
                        binding.editTotalChapters.setText(String.valueOf(chapter.getTotalChapters()));
                        binding.editChapterContent.setText(chapter.getContent());
                        break;
                    }
                }
            }
        });
    }

    private void saveChapter() {
        String title = binding.editChapterTitle.getText().toString().trim();
        String content = binding.editChapterContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please enter title and content", Toast.LENGTH_SHORT).show();
            return;
        }

        int currentChapter;
        int totalChapters;

        try {
            currentChapter = Integer.parseInt(binding.editCurrentChapter.getText().toString().trim());
            totalChapters = Integer.parseInt(binding.editTotalChapters.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid chapter numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentChapter <= 0 || totalChapters <= 0 || currentChapter > totalChapters) {
            Toast.makeText(this, "Please enter valid chapter numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        Chapter chapter = new Chapter(title, content, currentChapter, totalChapters, storyId);

        if (isEditMode && chapterId != -1) {
            chapter.setId(chapterId);
            storyViewModel.updateChapter(chapter); // <- Cập nhật
        } else {
            storyViewModel.insertChapter(chapter); // <- Thêm mới
        }


        Toast.makeText(this, "Chapter saved", Toast.LENGTH_SHORT).show();

        if (!isEditMode) {
            setResult(RESULT_OK);
        }
    }


    private void clearFieldsForNextChapter() {
        try {
            int nextChapter = Integer.parseInt(binding.editCurrentChapter.getText().toString().trim()) + 1;
            binding.editChapterTitle.setText("");
            binding.editChapterContent.setText("");
            binding.editCurrentChapter.setText(String.valueOf(nextChapter));
            binding.editTotalChapters.setText(String.valueOf(Math.max(nextChapter, Integer.parseInt(binding.editTotalChapters.getText().toString().trim()))));
        } catch (NumberFormatException e) {
            // fallback
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
