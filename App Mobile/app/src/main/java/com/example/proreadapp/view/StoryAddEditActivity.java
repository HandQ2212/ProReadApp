package com.example.proreadapp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.proreadapp.R;
import com.example.proreadapp.adapter.MultiSelectionAdapter;
import com.example.proreadapp.databinding.ActivityStoryAddEditBinding;
import com.example.proreadapp.databinding.DialogAddCategoryBinding;
import com.example.proreadapp.model.Category;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.viewmodel.CategoryViewModel;
import com.example.proreadapp.viewmodel.StoryViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public class StoryAddEditActivity extends AppCompatActivity {

    public static final String EXTRA_STORY_ID = "com.example.storyapp.EXTRA_STORY_ID";

    private ActivityStoryAddEditBinding binding;
    private StoryViewModel storyViewModel;
    private CategoryViewModel categoryViewModel;
    private MultiSelectionAdapter categoryAdapter;

    private String storyId;
    private Uri selectedImageUri = null;
    private boolean isEditMode = false;

    private static final int PERMISSION_REQUEST_CODE = 2001;
    private ActivityResultLauncher<String> pickMediaLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoryAddEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        storyViewModel = new ViewModelProvider(this).get(StoryViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        storyId = getIntent().getStringExtra(EXTRA_STORY_ID);
        isEditMode = storyId != null;

        if (!isEditMode) {
            storyId = UUID.randomUUID().toString();
            binding.imageStoryCover.setImageResource(R.drawable.ic_image_placeholder);
        } else {
            loadStoryData(storyId);
        }

        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        Uri savedUri = copyImageToInternalStorage(uri);
                        selectedImageUri = savedUri != null ? savedUri : uri;
                        Glide.with(this).load(selectedImageUri).into(binding.imageStoryCover);
                    }
                });

        binding.btnSelectImage.setOnClickListener(v -> {
            if (checkAndRequestPermissions()) openImagePicker();
        });

        binding.btnSaveStory.setOnClickListener(v -> saveStory());

        setupCategoryList();
        binding.btnSelectCategories.setOnClickListener(v -> showAddCategoryDialog());
    }

    private void setupCategoryList() {
        categoryAdapter = new MultiSelectionAdapter();
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerCategories.setAdapter(categoryAdapter);

        categoryViewModel.getAllCategories().observe(this, categories -> {
            categoryAdapter.submitList(categories);
            if (isEditMode) {
                storyViewModel.getCategoriesByStoryId(storyId).observe(this, selected -> {
                    categoryAdapter.setSelectedCategories(selected);
                });
            }
        });
    }

    private void showAddCategoryDialog() {
        DialogAddCategoryBinding dialogBinding = DialogAddCategoryBinding.inflate(LayoutInflater.from(this));
        new AlertDialog.Builder(this)
                .setTitle("Thêm thể loại")
                .setView(dialogBinding.getRoot())
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String name = dialogBinding.editCategoryName.getText().toString().trim();
                    if (!name.isEmpty()) {
                        categoryViewModel.insertCategory(new Category(name));
                    } else {
                        Toast.makeText(this, "Tên thể loại không được để trống", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void loadStoryData(String id) {
        storyViewModel.getStoryById(id).observe(this, story -> {
            if (story != null) {
                binding.editStoryTitle.setText(story.getTitle());
                binding.editStoryAuthor.setText(story.getAuthor());
                binding.editStoryInfo.setText(story.getInfo());
                binding.editStoryDescription.setText(story.getDescription());
                Glide.with(this)
                        .load(story.getImageUri() != null ? Uri.parse(story.getImageUri()) : R.drawable.ic_image_placeholder)
                        .into(binding.imageStoryCover);
            }
        });
    }

    private Uri copyImageToInternalStorage(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            File file = new File(getFilesDir(), "story_" + System.currentTimeMillis() + ".jpg");
            try (OutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                return Uri.fromFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
                return false;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                return false;
            }
        }
        return true;
    }

    private void openImagePicker() {
        pickMediaLauncher.launch("image/*");
    }

    private void saveStory() {
        String title = binding.editStoryTitle.getText().toString().trim();
        String author = binding.editStoryAuthor.getText().toString().trim();
        String info = binding.editStoryInfo.getText().toString().trim();
        String description = binding.editStoryDescription.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên truyện và tác giả", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : null;

        Story story = new Story(storyId, title, author, info, description, R.drawable.ic_image_placeholder, imageUriString);
        storyViewModel.insertStory(story);
        story.setLastReadAt(0);
        story.setUpdatedAt(System.currentTimeMillis());
        // Cập nhật danh sách thể loại cho truyện
        List<Category> selectedCategories = categoryAdapter.getSelectedCategories();
        storyViewModel.insertStoryWithCategories(storyId, selectedCategories);

        Toast.makeText(this, "Đã lưu truyện", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ChapterAddEditActivity.class);
        intent.putExtra(EXTRA_STORY_ID, storyId);
        startActivity(intent);
        finish();
    }
}
