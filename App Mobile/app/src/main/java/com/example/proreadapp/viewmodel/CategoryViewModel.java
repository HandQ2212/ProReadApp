package com.example.proreadapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel{
    private final MutableLiveData<List<String>> categoryList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getCategories(){
        return categoryList;
    }

    public void addCategory(String category){
        List<String> current = categoryList.getValue();
        if (!current.contains(category)){
            current.add(category);
            categoryList.setValue(current);
        }
    }
}
