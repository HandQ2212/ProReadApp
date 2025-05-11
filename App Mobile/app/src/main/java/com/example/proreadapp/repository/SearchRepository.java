package com.example.proreadapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proreadapp.model.SearchItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchRepository {
    private static SearchRepository instance;
    private final ExecutorService executorService;

    private SearchRepository() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public static synchronized SearchRepository getInstance() {
        if (instance == null) {
            instance = new SearchRepository();
        }
        return instance;
    }

    public LiveData<List<SearchItem>> searchBooks(String query) {
        MutableLiveData<List<SearchItem>> searchResults = new MutableLiveData<>();

        executorService.execute(() -> {
            try {
                Thread.sleep(1000);//chờ

                // This would be replaced with actual API call or database query
                List<SearchItem> results = performSearch(query);

                searchResults.postValue(results);
            } catch (InterruptedException e) {
                e.printStackTrace();
                searchResults.postValue(new ArrayList<>());
            }
        });

        return searchResults;
    }

    private List<SearchItem> performSearch(String query) {
        List<SearchItem> results = new ArrayList<>();

        if (query != null && !query.isEmpty()) {
            results.add(new SearchItem(
                    "1",
                    "Đắc Nhân Tâm",
                    "Dale Carnegie",
                    "Cuốn sách nổi tiếng về nghệ thuật đối nhân xử thế",
                    "https://tiemsach.org/wp-content/uploads/2023/07/Ebook-Dac-nhan-tam.jpg"));

            results.add(new SearchItem(
                    "2",
                    "Nhà Giả Kim",
                    "Paulo Coelho",
                    "Câu chuyện về hành trình khám phá vận mệnh của chính mình",
                    "https://th.bing.com/th/id/OIP._yzetDs3gher9vBV-Ax_iAHaL0?w=124&h=198&c=7&r=0&o=7&cb=iwp2&pid=1.7&rm=3"));

            results.add(new SearchItem(
               "3","Title 1", "Author 1", "Đây là truyện VD 1", "https://th.bing.com/th/id/OIP.AB5pC1gH8M5eQ5SlSPJBJAHaLB?cb=iwp2&rs=1&pid=ImgDetMain"
            ));
            results.add(new SearchItem(
                    "3","Title 2", "Author 2", "Đây là truyện VD 2", "https://th.bing.com/th/id/OIP.AB5pC1gH8M5eQ5SlSPJBJAHaLB?cb=iwp2&rs=1&pid=ImgDetMain"
            ));
            results.add(new SearchItem(
                    "3","Title 3", "Author 3", "Đây là truyện VD 3", "https://th.bing.com/th/id/OIP.AB5pC1gH8M5eQ5SlSPJBJAHaLB?cb=iwp2&rs=1&pid=ImgDetMain"
            ));

            List<SearchItem> filteredResults = new ArrayList<>();
            for (SearchItem item : results) {
                if (item.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        item.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                    filteredResults.add(item);
                }
            }
            return filteredResults;
        }

        return results;
    }
}