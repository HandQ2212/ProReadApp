package com.example.proreadapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.databinding.DocTruyenXmlBinding;
import com.example.proreadapp.viewmodel.ChapterViewModel;
import com.example.proreadapp.viewmodel.ReaderSettingViewModel;

import java.util.ArrayList;

public class ChapterReaderActivity extends AppCompatActivity {

    private DocTruyenXmlBinding binding;
    private ChapterViewModel chapterViewModel;
    private ReaderSettingViewModel readerSettingViewModel;

    private ArrayList<Integer> chapterIds;
    private String currentFont = "Sans";
    private int currentFontSize = 16;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = DocTruyenXmlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chapterViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);
        readerSettingViewModel = new ViewModelProvider(this).get(ReaderSettingViewModel.class);
        readerSettingViewModel.loadFromPrefs(this);

        readerSettingViewModel.getSelectedFont().observe(this, font -> {
            currentFont = font;
            applyFontAndSize();
        });

        readerSettingViewModel.getFontSize().observe(this, size -> {
            currentFontSize = size;
            applyFontAndSize();
        });

        binding.btnPrevious.setVisibility(View.VISIBLE);
        binding.btnNext.setVisibility(View.VISIBLE);

        chapterIds = getIntent().getIntegerArrayListExtra("chapter_ids");
        if (chapterIds == null || chapterIds.isEmpty()) {
            Toast.makeText(this, "Không có chương để đọc", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        int startChapterId = getIntent().getIntExtra("chapter_id", chapterIds.get(0));
        currentIndex = chapterIds.indexOf(startChapterId);
        if (currentIndex == -1) {
            currentIndex = 0;
        }

        loadChapterById(chapterIds.get(currentIndex));

        binding.btnPrevious.setOnClickListener(v -> {
            Log.d("ChapterReaderActivity", "Clicked Previous button");
            if (currentIndex <= 0) {
                Toast.makeText(this, "Đây là chương đầu tiên", Toast.LENGTH_SHORT).show();
                return;
            }
            currentIndex--;
            loadChapterById(chapterIds.get(currentIndex));
        });

        binding.btnNext.setOnClickListener(v -> {
            Log.d("ChapterReaderActivity", "Clicked Next button");
            if (currentIndex >= chapterIds.size() - 1) {
                Toast.makeText(this, "Đây là chương cuối", Toast.LENGTH_SHORT).show();
                return;
            }
            currentIndex++;
            loadChapterById(chapterIds.get(currentIndex));
        });
    }

    private void loadChapterById(int chapterId) {
        chapterViewModel.getChapterById(chapterId).observe(this, chapter -> {
            if (chapter != null) {
                binding.textChapterTitle.setText(chapter.getTitle());
                binding.textChapterContent.setText(chapter.getContent());
            } else {
                Toast.makeText(this, "Không tìm thấy chương", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyFontAndSize() {
        switch (currentFont) {
            case "Sans":
                binding.textChapterContent.setTypeface(android.graphics.Typeface.SANS_SERIF);
                break;
            case "Serif":
                binding.textChapterContent.setTypeface(android.graphics.Typeface.SERIF);
                break;
            case "Monospace":
                binding.textChapterContent.setTypeface(android.graphics.Typeface.MONOSPACE);
                break;
            default:
                binding.textChapterContent.setTypeface(android.graphics.Typeface.DEFAULT);
        }
        binding.textChapterContent.setTextSize(currentFontSize);
    }
}
