package com.example.proreadapp.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.databinding.DocTruyenXmlBinding;
import com.example.proreadapp.viewmodel.ChapterViewModel;

public class ChapterReaderActivity extends AppCompatActivity {

    private DocTruyenXmlBinding binding;
    private ChapterViewModel chapterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = DocTruyenXmlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int chapterId = getIntent().getIntExtra("chapter_id", -1);

        chapterViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);

        chapterViewModel.getChapterById(chapterId).observe(this, chapter -> {
            if (chapter != null) {
                binding.textChapterTitle.setText(chapter.getTitle());
                binding.textChapterContent.setText(chapter.getContent());
            }
        });
    }
}
