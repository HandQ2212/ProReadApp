package com.example.proreadapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.proreadapp.model.Story;
import com.example.proreadapp.repository.StoryRepository;
import com.example.proreadapp.view.StoryDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowListViewModel extends AndroidViewModel {
    private final StoryRepository repository;
    private final MutableLiveData<List<Story>> storyList = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ShowListViewModel(@NonNull Application application) {
        super(application);
        repository = new StoryRepository(application);
    }

    public LiveData<List<Story>> getStoryList() {
        return storyList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setStoryIds(List<String> storyIds) {
        if (storyIds == null || storyIds.isEmpty()) {
            errorMessage.setValue("Không có ID truyện để tải");
            return;
        }

        List<Story> loadedStories = new ArrayList<>();
        List<String> remainingIds = new ArrayList<>(storyIds);

        for (String id : storyIds) {
            LiveData<Story> liveData = repository.getStoryById(id);
            liveData.observeForever(new Observer<Story>() {
                @Override
                public void onChanged(Story story) {
                    if (story != null) {
                        loadedStories.add(story);
                    }

                    liveData.removeObserver(this);
                    remainingIds.remove(id);

                    if (remainingIds.isEmpty()) {
                        if (loadedStories.isEmpty()) {
                            errorMessage.postValue("Không tìm thấy truyện nào");
                        } else {
                            storyList.postValue(loadedStories);
                        }
                    }
                }
            });
        }
    }


    public void setStoryList(List<Story> stories) {
        storyList.setValue(stories);
    }

    public void onStoryClicked(Context context, String storyId) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra("storyId", storyId);
        context.startActivity(intent);
    }

    public interface StoryCallback {
        void onResult(Story story);
        void onError(String message);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
