package com.example.proreadapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.R;
import com.example.proreadapp.databinding.FragmentSettingBinding;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.viewmodel.StoryViewModel;

public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);

        setUpDefaultTheme();
        setUpSwitchListener();

        return binding.getRoot();
    }

    public void setUpDefaultTheme() {
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        binding.switchTheme.setChecked(currentMode == AppCompatDelegate.MODE_NIGHT_YES);
    }

    public void setUpSwitchListener() {
        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) setDarkMode();
            else setLightMode();
        });
    }

    public void setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    public void setLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}