package com.example.proreadapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.model.Chapter;

public class ChapterViewModel extends ViewModel {
    private final MutableLiveData<Chapter> chapterLiveData = new MutableLiveData<>();

    public LiveData<Chapter> getChapterLiveData() {
        return chapterLiveData;
    }

    public ChapterViewModel() {
        loadChapter();
    }

    public void loadChapter() {
        String content = "Tư bà bà phẩn khởi bừng bừng kéo nó quay vào thôn, cười nói: \"Đừng nhìn nữa, đến đây nhanh lên, hôm nay là ngày trọng đại của con! Trưởng thôn, Mã gia, ra đây hết đi!\"\n\n"
                + "Trong thôn nhóm lên lửa trại, trưởng thôn lại được người dùng cáng mang ra, trầm giọng nói: \"Tử Linh đều tìm đủ rồi sao?\"\n\n"
                + "\"Đã tìm đủ.\"\n\n"
                + "Mã gia cụt tay kéo một con rắn vẫn còn sống...";

        Chapter chapter = new Chapter(
                "Chương 2: Máu Tử Linh",
                content,
                2,
                1349
        );
        chapterLiveData.setValue(chapter);
    }

    public void nextChapter() {
        // TODO: load next chapter
    }

    public void previousChapter() {
        // TODO: load previous chapter
    }
}
