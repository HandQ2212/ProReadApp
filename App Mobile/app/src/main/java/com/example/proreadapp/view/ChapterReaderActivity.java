package com.example.proreadapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.databinding.DocTruyenXmlBinding;
import com.example.proreadapp.viewmodel.ChapterViewModel;

import java.util.ArrayList;

public class ChapterReaderActivity extends AppCompatActivity {

    private DocTruyenXmlBinding binding;
    private ChapterViewModel chapterViewModel;

    private ArrayList<Integer> chapterIds;
    private int currentIndex = 0; // vị trí chương hiện tại trong danh sách

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = DocTruyenXmlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chapterViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);

        // Hiện nút chuyển chương
        binding.btnPrevious.setVisibility(View.VISIBLE);
        binding.btnNext.setVisibility(View.VISIBLE);

        // Lấy danh sách chapter_id từ Intent
        chapterIds = getIntent().getIntegerArrayListExtra("chapter_ids");
        if (chapterIds == null || chapterIds.isEmpty()) {
            Toast.makeText(this, "Không có chương để đọc", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Lấy chapter_id khởi đầu từ Intent (nếu có), nếu không thì đọc chương đầu tiên
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
}
