package com.example.proreadapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.R;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.viewmodel.StoryViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class StoryAddEditActivity extends AppCompatActivity {

    public static final String EXTRA_STORY_ID = "com.example.storyapp.EXTRA_STORY_ID";
    private static final int PICK_IMAGE_REQUEST_CODE = 1001;

    private TextInputEditText editTitle, editAuthor, editInfo, editDescription;
    private ImageView imageStoryCover;
    private Button btnSelectImage, btnSaveStory;

    private StoryViewModel storyViewModel;

    private String storyId;
    private Uri selectedImageUri = null;
    private boolean isEditMode = false;

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

        btnSelectImage.setOnClickListener(v -> openImagePicker());
        btnSaveStory.setOnClickListener(v -> saveStory());
    }

    private void loadStoryData(String id) {
        Story story = storyViewModel.getStoryById(id);
        if (story != null) {
            editTitle.setText(story.getTitle());
            editAuthor.setText(story.getAuthor());
            editInfo.setText(story.getInfo());
            editDescription.setText(story.getDescription());

            imageStoryCover.setImageResource(R.drawable.ic_image_placeholder);
        }
    }


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imageStoryCover.setImageURI(selectedImageUri);
            }
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
        int defaultImageResId = R.drawable.ic_image_placeholder; // hoặc giá trị mặc định phù hợp

        // Tạo đối tượng Story với constructor full
        Story story = new Story(storyId, title, author, info, description, defaultImageResId);

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
