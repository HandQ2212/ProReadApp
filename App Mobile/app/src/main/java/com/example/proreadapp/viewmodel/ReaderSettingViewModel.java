package com.example.proreadapp.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReaderSettingViewModel extends ViewModel{
    private final MutableLiveData<String> selectedFont = new MutableLiveData<>("Sans");
    private final MutableLiveData<Integer> fontSize = new MutableLiveData<>(16);

    public LiveData<String> getSelectedFont(){
        return selectedFont;
    }

    public LiveData<Integer> getFontSize(){
        return fontSize;
    }

    public void setSelectedFont(String font){
        selectedFont.setValue(font);
    }

    public void setFontSize(int size){
        fontSize.setValue(size);
    }

    public void loadFromPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("reader_settings", Context.MODE_PRIVATE);
        String font = prefs.getString("selected_font", "Sans");
        int size = prefs.getInt("font_size", 16);
        selectedFont.setValue(font);
        fontSize.setValue(size);
    }

    public void saveToPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("reader_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selected_font", selectedFont.getValue());
        editor.putInt("font_size", fontSize.getValue() != null ? fontSize.getValue() : 16);
        editor.apply();
    }

}
