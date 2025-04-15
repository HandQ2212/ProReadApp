package com.example.proreadapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.proreadapp.databinding.FragmentTimKiemBinding;

public class TimKiemFragment extends Fragment {
    private FragmentTimKiemBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentTimKiemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
