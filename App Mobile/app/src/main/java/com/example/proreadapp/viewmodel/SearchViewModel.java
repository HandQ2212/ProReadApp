package com.example.proreadapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.model.SearchItem;
import com.example.proreadapp.repository.SearchRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private final SearchRepository repository;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");

    public SearchViewModel() {
        repository = SearchRepository.getInstance();
    }

    public LiveData<List<SearchItem>> searchBooks(String query) {
        isLoading.setValue(true);
        LiveData<List<SearchItem>> searchResults = repository.searchBooks(query);

        // Setting loading to false would typically be done when the search results are received
        // For simplicity, we'll assume the repository handles this for now
        // In a real application, you'd use a callback or observeForever to detect when data is loaded
        isLoading.setValue(false);

        return searchResults;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }
}