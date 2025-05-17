package com.example.proreadapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.R;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.repository.StoryRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final StoryRepository storyRepository;

    private final LiveData<List<Story>> newestStoryList;
    private final LiveData<List<Story>> recentlyUpdatedStoryList;
    private final LiveData<List<Story>> completeStoryList;

    private final MutableLiveData<List<String>> selectedStoryIds = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToShowList = new MutableLiveData<>();

    public HomeViewModel(StoryRepository repository) {
        this.storyRepository = repository;

        newestStoryList = storyRepository.getNewestStories();
        recentlyUpdatedStoryList = storyRepository.getRecentlyUpdatedStories();
        completeStoryList = storyRepository.getCompleteStories();
    }

    // Nếu dùng ViewModelProvider.Factory thì cần constructor có tham số StoryRepository

    public LiveData<List<Story>> getNewestStoryList() {
        return newestStoryList;
    }

    public LiveData<List<Story>> getRecentlyUpdatedStoryList() {
        return recentlyUpdatedStoryList;
    }

    public LiveData<List<Story>> getCompleteStoryList() {
        return completeStoryList;
    }

    public LiveData<List<String>> getSelectedStoryIds() {
        return selectedStoryIds;
    }

    public LiveData<Boolean> getNavigateFlag() {
        return navigateToShowList;
    }

    public void resetNavigation() {
        navigateToShowList.setValue(false);
    }

    public void onFavoriteSectionClicked(List<Story> storyList) {
        List<String> ids = new ArrayList<>();
        for (Story story : storyList) {
            ids.add(story.getId());
        }
        selectedStoryIds.setValue(ids);
        navigateToShowList.setValue(true);
    }

    public void onMostViewSectionClicked(List<Story> storyList) {
        List<String> ids = new ArrayList<>();
        for (Story story : storyList) {
            ids.add(story.getId());
        }
        selectedStoryIds.setValue(ids);
        navigateToShowList.setValue(true);
    }

    public void onTrendingSectionClicked(List<Story> storyList) {
        List<String> ids = new ArrayList<>();
        for (Story story : storyList) {
            ids.add(story.getId());
        }
        selectedStoryIds.setValue(ids);
        navigateToShowList.setValue(true);
    }
}

