package com.example.proreadapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.proreadapp.R;
import com.example.proreadapp.adapter.StoryAdapter;
import com.example.proreadapp.databinding.FragmentOfflineBinding;
import com.example.proreadapp.model.Story;

import java.util.ArrayList;
import java.util.List;

public class OfflineFragment extends Fragment{
    private FragmentOfflineBinding binding;
    private List<Story> storyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentOfflineBinding.inflate(inflater, container, false);
        initData();
        setupRecyclerView();
        return binding.getRoot();
    }


    private void initData(){
        storyList = new ArrayList<>();
        storyList.add(new Story(
                "Sample Title 1",
                "Sample Author 1",
                "Sample Info 1",
                "Sample Description 1",
                R.drawable.mucthanky1618392290// Thay bằng ID ảnh trong thư mục res/drawable
        ));
    }

    private void setupRecyclerView(){
        StoryAdapter storyAdapter = new StoryAdapter(requireContext(), storyList);
        binding.recyclerViewOffline.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerViewOffline.setAdapter(storyAdapter);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}