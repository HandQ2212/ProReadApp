package com.example.proreadapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.model.Chapter;

public class ChapterViewModel extends ViewModel {
    private final MutableLiveData<Chapter> chapterLiveData = new MutableLiveData<>();
    private int currentChapterId = 1;

    public LiveData<Chapter> getChapterLiveData() {
        return chapterLiveData;
    }

    public ChapterViewModel() {
        loadChapterById(currentChapterId);
    }

    public void loadChapterById(int chapterId) {
        // TODO: Thay thế bằng logic lấy dữ liệu từ cơ sở dữ liệu Room
        String content = "Tư bà bà phẩn khởi bừng bừng kéo nó quay vào thôn, cười nói: \"Đừng nhìn nữa, đến đây nhanh lên, hôm nay là ngày trọng đại của con! Trưởng thôn, Mã gia, ra đây hết đi!\"\n\n"
                + "Trong thôn nhóm lên lửa trại, trưởng thôn lại được người dùng cáng mang ra, trầm giọng nói: \"Tử Linh đều tìm đủ rồi sao?\"\n\n"
                + "\"Đã tìm đủ.\"\n\n"
                + "Mã gia cụt tay kéo một con rắn vẫn còn sống...";

        Chapter chapter = new Chapter(
                "Chương " + chapterId + ": Máu Tử Linh",
                content,
                chapterId,
                1349, 1
        );
        chapterLiveData.setValue(chapter);
    }

    public void nextChapter() {
        currentChapterId++;
        loadChapterById(currentChapterId);
    }

    public void previousChapter() {
        if (currentChapterId > 1) {
            currentChapterId--;
            loadChapterById(currentChapterId);
        }
    }
}