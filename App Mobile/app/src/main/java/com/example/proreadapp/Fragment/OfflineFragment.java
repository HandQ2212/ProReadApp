package com.example.proreadapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proreadapp.R;
import com.example.proreadapp.databinding.FragmentOfflineBinding;

public class OfflineFragment extends Fragment {
    private FragmentOfflineBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentOfflineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
