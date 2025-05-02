package com.example.proreadapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.databinding.FragmentSettingBinding;
import com.example.proreadapp.viewmodel.ReaderSettingViewModel;

public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;
    private ReaderSettingViewModel readerSettingViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        readerSettingViewModel = new ViewModelProvider(requireActivity()).get(ReaderSettingViewModel.class);

        setupThemeSwitch();
        setupFontSettingsButton();

        return binding.getRoot();
    }

    private void setupThemeSwitch() {
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        binding.switchTheme.setChecked(currentMode == AppCompatDelegate.MODE_NIGHT_YES);

        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });
    }

    private void setupFontSettingsButton() {
        binding.btnFontSettings.setOnClickListener(v -> showFontSettingsDialog());
    }

    private void showFontSettingsDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(
                com.example.proreadapp.R.layout.dialog_font_settings, null
        );

        android.widget.Spinner fontSpinner = dialogView.findViewById(com.example.proreadapp.R.id.spinner_font);
        android.widget.SeekBar fontSizeSeekBar = dialogView.findViewById(com.example.proreadapp.R.id.seekbar_font_size);
        android.widget.TextView fontSizePreview = dialogView.findViewById(com.example.proreadapp.R.id.text_font_size_preview);

        String[] fonts = {"Sans", "Serif", "Monospace"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, fonts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(adapter);

        // Observe current settings
        readerSettingViewModel.getSelectedFont().observe(getViewLifecycleOwner(), font -> {
            int index = java.util.Arrays.asList(fonts).indexOf(font);
            if (index >= 0) fontSpinner.setSelection(index);
        });

        readerSettingViewModel.getFontSize().observe(getViewLifecycleOwner(), size -> {
            fontSizeSeekBar.setProgress(size);
            fontSizePreview.setText("Kích thước: " + size );
        });

        fontSizeSeekBar.setMax(30);
        fontSizeSeekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                fontSizePreview.setText("Kích thước: " + progress);
            }
            @Override public void onStartTrackingTouch(android.widget.SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(android.widget.SeekBar seekBar) {}
        });

        new AlertDialog.Builder(requireContext())
                .setTitle("Cài đặt Font & Kích thước")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    readerSettingViewModel.setSelectedFont(fontSpinner.getSelectedItem().toString());
                    readerSettingViewModel.setFontSize(fontSizeSeekBar.getProgress());
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
