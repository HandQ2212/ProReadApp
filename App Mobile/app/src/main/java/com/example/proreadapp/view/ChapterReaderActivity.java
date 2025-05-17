package com.example.proreadapp.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proreadapp.R;

public class ChapterReaderActivity extends AppCompatActivity{

    private TextView textTitle, textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_truyen_xml);

        textTitle = findViewById(R.id.textChapterTitle);
        textContent = findViewById(R.id.textChapterContent);

        // Lấy dữ liệu từ Intent
        int chapterId = getIntent().getIntExtra("chapter_id", 1);
        String storyTitle = getIntent().getStringExtra("story_title");

        // Gán tiêu đề chương (tạm thời, sau có thể lấy từ server)
        textTitle.setText("Chương " + chapterId + ": Tiêu đề chương mẫu");
        textContent.setText("Đây là nội dung của chương " + chapterId + " thuộc truyện " + storyTitle + "abcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmnoabcdefghiklmno");
    }
}

