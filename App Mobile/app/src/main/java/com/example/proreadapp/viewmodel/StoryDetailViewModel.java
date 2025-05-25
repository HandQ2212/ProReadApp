package com.example.proreadapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.dao.ChapterDao;
import com.example.proreadapp.database.StoryDatabase;
import com.example.proreadapp.model.Chapter;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.repository.StoryRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StoryDetailViewModel extends AndroidViewModel {
    private final StoryRepository repository;
    private final ChapterDao chapterDao;
    private final MutableLiveData<Story> storyLiveData = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public StoryDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new StoryRepository(application);
        chapterDao = StoryDatabase.getInstance(application).chapterDao();
    }

    public void loadStoryById(String id) {
        repository.getStoryById(id).observeForever(story -> {
            storyLiveData.setValue(story);
        });
    }

    public LiveData<Story> getStoryLiveData() {
        return storyLiveData;
    }

    public LiveData<List<Chapter>> getChaptersByStoryId(String storyId) {
        return chapterDao.getChaptersByStoryId(storyId);
    }

    public void updateLastReadTime(String storyId) {
        executor.execute(() -> {
            repository.updateLastReadTime(storyId, System.currentTimeMillis());
        });    }


}
