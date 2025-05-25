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
