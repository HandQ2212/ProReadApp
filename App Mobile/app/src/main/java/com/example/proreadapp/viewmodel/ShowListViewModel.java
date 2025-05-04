package com.example.proreadapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.model.Story;

import java.util.List;

public class ShowListViewModel extends ViewModel{
    private final MutableLiveData<List<Story>> storyList = new MutableLiveData<>();

    public void setStoryList(List<Story> stories){
        storyList.setValue(stories);
    }

    public LiveData<List<Story>> getStoryList(){
        return storyList;
    }
}
