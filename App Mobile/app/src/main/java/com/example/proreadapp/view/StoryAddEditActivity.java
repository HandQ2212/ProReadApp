package com.example.proreadapp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.proreadapp.R;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.viewmodel.StoryViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class StoryAddEditActivity extends AppCompatActivity {

    public static final String EXTRA_STORY_ID = "com.example.storyapp.EXTRA_STORY_ID";

    private TextInputEditText editTitle, editAuthor, editInfo, editDescription;
    private ImageView imageStoryCover;
    private Button btnSelectImage, btnSaveStory;

    private StoryViewModel storyViewModel;

    private String storyId;
    private Uri selectedImageUri = null;
    private boolean isEditMode = false;

    private static final int PERMISSION_REQUEST_CODE = 2001;
    private ActivityResultLauncher<String> pickMediaLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_story_add_edit);

        editTitle = findViewById(R.id.edit_story_title);
        editAuthor = findViewById(R.id.edit_story_author);
        editInfo = findViewById(R.id.edit_story_info);
        editDescription = findViewById(R.id.edit_story_description);
        imageStoryCover = findViewById(R.id.image_story_cover);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnSaveStory = findViewById(R.id.btn_save_story);
        storyViewModel = new ViewModelProvider(this).get(StoryViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_STORY_ID)) {
            storyId = intent.getStringExtra(EXTRA_STORY_ID);
            isEditMode = true;
            loadStoryData(storyId);
        } else {
            isEditMode = false;
            storyId = UUID.randomUUID().toString();
            imageStoryCover.setImageResource(R.drawable.ic_image_placeholder);
        }

        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        Uri savedUri = copyImageToInternalStorage(uri);
                        if (savedUri != null) {
                            selectedImageUri = savedUri;
                            Glide.with(this).load(selectedImageUri).into(imageStoryCover);
                        } else {
                            selectedImageUri = uri;
                            Glide.with(this).load(selectedImageUri).into(imageStoryCover);
                        }
                    } else {
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnSelectImage.setOnClickListener(v -> {
            if (checkAndRequestPermissions()) {
                openImagePicker();
            }
        });

        btnSaveStory.setOnClickListener(v -> saveStory());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Permission denied to access images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImagePicker() {
        pickMediaLauncher.launch("image/*");
    }

    private void loadStoryData(String id) {
        storyViewModel.getStoryById(id).observe(this, story -> {
            if (story != null) {
                editTitle.setText(story.getTitle());
                editAuthor.setText(story.getAuthor());
                editInfo.setText(story.getInfo());
                editDescription.setText(story.getDescription());

                if (story.getImageUri() != null) {
                    Glide.with(this)
                            .load(Uri.parse(story.getImageUri()))
                            .placeholder(R.drawable.ic_image_placeholder)
                            .error(R.drawable.ic_image_placeholder)
                            .into(imageStoryCover);
                } else {
                    imageStoryCover.setImageResource(R.drawable.ic_image_placeholder);
                }
            }
        });
    }

    private Uri copyImageToInternalStorage(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            String fileName = "story_" + System.currentTimeMillis() + ".jpg";
            File file = new File(getFilesDir(), fileName);
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

    private void saveStory() {
        String title = editTitle.getText().toString().trim();
        String author = editAuthor.getText().toString().trim();
        String info = editInfo.getText().toString().trim();
        String description = editDescription.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Please enter title and author", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : null;

        Story story = new Story(storyId, title, author, info, description, R.drawable.ic_image_placeholder, imageUriString);

        storyViewModel.insertStory(story);

        Toast.makeText(this, "Story saved", Toast.LENGTH_SHORT).show();

        if (!isEditMode) {
            isEditMode = true;
        }

        Intent intent = new Intent(this, ChapterAddEditActivity.class);
        intent.putExtra(EXTRA_STORY_ID, storyId);
        startActivity(intent);

        finish();
    }
}