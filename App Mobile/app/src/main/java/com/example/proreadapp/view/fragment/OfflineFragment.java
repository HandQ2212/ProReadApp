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
    //Cac story sau se duoc thay bang id de giam thieu thoi gian truy cap database
    private List<Story> recentlyReadStoryList;
    private List<Story> favoriteStoryList;
    private List<Story> downloadedStoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentOfflineBinding.inflate(inflater, container, false);
        initData();
        setupRecentlyReadStoryList();
        setupFavoriteStoryList();
        setupDownloadedStoryList();
        return binding.getRoot();
    }


    private void initData(){
        recentlyReadStoryList = new ArrayList<>();
        recentlyReadStoryList.add(new Story(
                "Sample Title 1",
                "Sample Author 1",
                "Sample Info 1",
                "Sample Description 1",
                R.drawable.mucthanky1618392290// Thay bằng ID ảnh trong thư mục res/drawable
        ));

        favoriteStoryList = new ArrayList<>();
        favoriteStoryList.add(new Story(
                "Sample Title 2",
                "Sample Author 2",
                "Sample Info 2",
                "Sample Description 2",
                R.drawable.mucthanky1618392290
        ));

        downloadedStoryList = new ArrayList<>();
        downloadedStoryList.add(new Story(
                "Sample Title 3",
                "Sample Author 3",
                "Sample Info 3",
                "Sample Description 3",
                R.drawable.mucthanky1618392290
        ));
    }

    private void setupRecentlyReadStoryList(){
        StoryAdapter storyAdapter = new StoryAdapter(requireContext(), recentlyReadStoryList);
        binding.recyclerViewOffline.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerViewOffline.setAdapter(storyAdapter);
    }

    private void setupFavoriteStoryList(){
        StoryAdapter storyAdapter = new StoryAdapter(requireContext(), favoriteStoryList);
        binding.recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerViewFavorites.setAdapter(storyAdapter);
    }

    private void setupDownloadedStoryList(){
        StoryAdapter storyAdapter = new StoryAdapter(requireContext(), downloadedStoryList);
        binding.recyclerViewDownloads.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerViewDownloads.setAdapter(storyAdapter);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}