package com.example.proreadapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.proreadapp.model.Story;

public class StoryDetailViewModel extends AndroidViewModel{
    private final MutableLiveData<Story> storyLiveData = new MutableLiveData<>();

    public StoryDetailViewModel(@NonNull Application application){
        super(application);
    }

    public MutableLiveData<Story> getStoryLiveData(){
        return storyLiveData;
    }

    public void setStoryData(String id, String title, String author, String info, String description, int imageResId){
        Story story = new Story(id, title, author, info, description, imageResId);
        storyLiveData.setValue(story);
    }
}
