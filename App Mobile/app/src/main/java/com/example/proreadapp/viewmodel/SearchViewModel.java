package com.example.proreadapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.proreadapp.model.SearchItem;
import com.example.proreadapp.repository.SearchRepository;

import java.util.List;import android.util.Log;


public class SearchViewModel extends ViewModel {
    private SearchRepository repository;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    // LiveData kết quả search sẽ tự động cập nhật khi searchQuery thay đổi


    public SearchViewModel(Context context) {
        repository = SearchRepository.getInstance(context);
    }

    private final LiveData<List<SearchItem>> searchResults = Transformations.switchMap(searchQuery, query -> {
        if (query == null || query.trim().isEmpty()) {
            return new MutableLiveData<>(List.of());
        } else {
            isLoading.setValue(true);
            Log.d("TimKiemFragment", "Tìm kiếm với từ khóa: " + query);
            LiveData<List<SearchItem>> results = repository.searchBooks(query);


            return results;
        }
    });

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public LiveData<List<SearchItem>> getSearchResults() {
        return searchResults;
    }


}
