package com.example.proreadapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.R;
import com.example.proreadapp.model.Story;

import java.util.ArrayList;
import java.util.List;

public class OfflineViewModel extends ViewModel{
    private final MutableLiveData<List<Story>> recentlyReadStories = new MutableLiveData<>();
    private final MutableLiveData<List<Story>> favoriteStories = new MutableLiveData<>();
    private final MutableLiveData<List<Story>> downloadedStories = new MutableLiveData<>();

    public OfflineViewModel(){
        loadMockData(); // dung firebase thi thay repository sau nay
    }

    private void loadMockData(){
//        List<Story> list1 = new ArrayList<>();
//        list1.add(new Story("13","Title 1", "Author 1", "...", "...", R.drawable.mucthanky));
//        list1.add(new Story("14","Title 2", "Author 2", "...", "...", R.drawable.mucthanky));
//        list1.add(new Story("15","Title 3", "Author 3", "...", "...", R.drawable.mucthanky));
//        list1.add(new Story("16","Title 4", "Author 4", "...", "...", R.drawable.mucthanky));
//        recentlyReadStories.setValue(list1);
//
//        List<Story> list2 = new ArrayList<>();
//        list2.add(new Story("17","Title 2", "Author 2", "...", "...", R.drawable.mucthanky));
//        favoriteStories.setValue(list2);
//
//        List<Story> list3 = new ArrayList<>();
//        list3.add(new Story("18","Title 3", "Author 3", "...", "...", R.drawable.mucthanky));
//        downloadedStories.setValue(list3);
    }

    public LiveData<List<Story>> getRecentlyReadStories(){
        return recentlyReadStories;
    }

    public LiveData<List<Story>> getFavoriteStories(){
        return favoriteStories;
    }

    public LiveData<List<Story>> getDownloadedStories(){
        return downloadedStories;
    }
}
