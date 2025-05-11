package com.example.proreadapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proreadapp.database.StoryDatabase;
import com.example.proreadapp.repository.StoryRepository;
import com.example.proreadapp.model.Story;

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
        StoryDatabase database = StoryDatabase.getInstance(application);
        repository = new StoryRepository((Application) database.storyDao());
    }

    // Getter for story list LiveData
    public LiveData<List<Story>> getStoryList() {
        return storyList;
    }

    // Getter for error message LiveData
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Setter for story IDs and trigger load
    public void setStoryIds(List<String> storyIds) {
        if (storyIds == null || storyIds.isEmpty()) {
            errorMessage.setValue("Không có ID truyện để tải");
            return;
        }

        // Tải danh sách Story từ Room Database bằng IDs
        executorService.execute(() -> {
            List<Story> stories = new ArrayList<>();
            try {
                for (String id : storyIds) {
                    Story story = repository.getStoryById(id);
                    if (story != null) {
                        stories.add(story);
                    }
                }

                // Cập nhật UI trên main thread
                MutableLiveData<List<Story>> finalStoryList = storyList;
                if (stories.isEmpty()) {
                    errorMessage.postValue("Không tìm thấy truyện nào từ các ID đã cung cấp");
                } else {
                    finalStoryList.postValue(stories);
                }
            } catch (Exception e) {
                errorMessage.postValue("Lỗi khi tải dữ liệu: " + e.getMessage());
            }
        });
    }
    public void setStoryList(List<Story> stories) {
        storyList.setValue(stories);
    }

    // Handle click on a story by ID
    public void onStoryClicked(Context context, String storyId) {
        // Launch StoryDetailActivity with just the ID
        Intent intent = new Intent(context, com.example.proreadapp.view.StoryDetailActivity.class);
        intent.putExtra("storyId", storyId);
        context.startActivity(intent);
    }

    // Method to get a story by ID from Room directly
    public void getStoryById(String storyId, StoryCallback callback) {
        executorService.execute(() -> {
            try {
                Story story = repository.getStoryById(storyId);
                callback.onResult(story);
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        });
    }

    // Callback interface for async story operations
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