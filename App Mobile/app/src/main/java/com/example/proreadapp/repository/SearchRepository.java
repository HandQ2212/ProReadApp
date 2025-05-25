package com.example.proreadapp.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.proreadapp.database.StoryDatabase;
import com.example.proreadapp.dao.StoryDao;
import com.example.proreadapp.model.SearchItem;
import com.example.proreadapp.model.Story;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchRepository {
    private static SearchRepository instance;
    private final ExecutorService executorService;
    private final StoryDao storyDao;

    private SearchRepository(Context context) {
        StoryDatabase db = StoryDatabase.getInstance(context);
        storyDao = db.storyDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static synchronized SearchRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SearchRepository(context);
        }
        return instance;
    }

    public LiveData<List<SearchItem>> searchBooks(String query) {
        LiveData<List<Story>> storiesLiveData = storyDao.searchStories(query);
        MutableLiveData<List<SearchItem>> searchResults = new MutableLiveData<>();
        storiesLiveData.observeForever(new Observer<List<Story>>() {
            @Override
            public void onChanged(List<Story> stories) {
                if (stories == null) {
                    searchResults.postValue(new ArrayList<>());
                    return;
                }

                List<SearchItem> items = new ArrayList<>();
                for (Story story : stories) {
                    items.add(new SearchItem(
                            story.getId(),
                            story.getTitle(),
                            story.getAuthor(),
                            story.getDescription(),
                            story.getImageUri() != null ? story.getImageUri() : ""
                    ));
                    Log.d("SearchRepo", "Tìm thấy: " + story.getTitle());
                }

                searchResults.postValue(items);
            }
        });

        return searchResults;
    }



}
