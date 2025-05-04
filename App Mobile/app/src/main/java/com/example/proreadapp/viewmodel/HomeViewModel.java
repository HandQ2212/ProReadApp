package com.example.proreadapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.R;
import com.example.proreadapp.model.Story;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel{
    private final MutableLiveData<List<String>> selectedStoryIds = new MutableLiveData<List<String>>();
    private final MutableLiveData<Boolean> navigateToShowList = new MutableLiveData<>();
    private final MutableLiveData<List<Story>> newestStoryList = new MutableLiveData<>();
    private final MutableLiveData<List<Story>> recentlyUpdatedStoryList = new MutableLiveData<>();
    private final MutableLiveData<List<Story>> completeStoryList = new MutableLiveData<>();

    public void onFavoriteSectionClicked(List<Story> storyList){
        List<String> ids = new ArrayList<>();
        for (Story story : storyList){
            ids.add(story.getId());
        }
        selectedStoryIds.setValue(ids);
        navigateToShowList.setValue(true);
    }

    public HomeViewModel(){
        loadMockData();
    }
    public void onMostViewSectionClicked(List<Story> storyList){
        List<String> ids = new ArrayList<>();
        for (Story story : storyList){
            ids.add(String.valueOf(story.getId()));
        }
        selectedStoryIds.setValue(ids);
        navigateToShowList.setValue(true);
    }

    public void onTrendingSectionClicked(List<Story> storyList){
        List<String> ids = new ArrayList<>();
        for (Story story : storyList){
            ids.add(String.valueOf(story.getId()));
        }
        selectedStoryIds.setValue(ids);
        navigateToShowList.setValue(true);
    }
    public void loadMockData(){
        List<Story> list1 = new ArrayList<>();
        list1.add(new Story("Title 1", "Author 1", "...", "...", R.drawable.mucthanky1618392290));
        list1.add(new Story("Title 2", "Author 2", "...", "...", R.drawable.mucthanky1618392290));
        list1.add(new Story("Title 3", "Author 3", "...", "...", R.drawable.mucthanky1618392290));
        list1.add(new Story("Title 4", "Author 4", "...", "...", R.drawable.mucthanky1618392290));
        newestStoryList.setValue(list1);

        List<Story> list2 = new ArrayList<>();
        list2.add(new Story("Title 2", "Author 2", "...", "...", R.drawable.mucthanky1618392290));
        recentlyUpdatedStoryList.setValue(list2);

        List<Story> list3 = new ArrayList<>();
        list3.add(new Story("Title 3", "Author 3", "...", "...", R.drawable.mucthanky1618392290));
        completeStoryList.setValue(list3);
    }

    public LiveData<List<Story>> getNewestStoryList(){
        return newestStoryList;
    }

    public LiveData<List<Story>> getRecentlyUpdatedStoryList(){
        return recentlyUpdatedStoryList;
    }

    public LiveData<List<Story>> getCompleteStoryList(){
        return completeStoryList;
    }

    public LiveData<List<String>> getSelectedStoryIds(){
        return selectedStoryIds;
    }

    public LiveData<Boolean> getNavigateFlag(){
        return navigateToShowList;
    }

    public void resetNavigation(){
        navigateToShowList.setValue(false);
    }
}
