package com.example.proreadapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReaderSettingViewModel extends ViewModel {
    private final MutableLiveData<String> selectedFont = new MutableLiveData<>("Sans");
    private final MutableLiveData<Integer> fontSize = new MutableLiveData<>(16);

    public LiveData<String> getSelectedFont() {
        return selectedFont;
    }

    public LiveData<Integer> getFontSize() {
        return fontSize;
    }

    public void setSelectedFont(String font) {
        selectedFont.setValue(font);
    }

    public void setFontSize(int size) {
        fontSize.setValue(size);
    }
}
