package com.example.proreadapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.proreadapp.R;
import com.example.proreadapp.adapter.StoryAdapter;
import com.example.proreadapp.databinding.FragmentOfflineBinding;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.viewmodel.OfflineViewModel;

import androidx.lifecycle.ViewModelProvider;

public class OfflineFragment extends Fragment{
    private FragmentOfflineBinding binding;
    private OfflineViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentOfflineBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(OfflineViewModel.class);

        observeData();

        return binding.getRoot();
    }
    private void observeData() {
        viewModel.getRecentlyReadStories().observe(getViewLifecycleOwner(), stories -> {
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories, new StoryAdapter.OnStoryClickListener() {
                @Override
                public void onStoryClick(String storyId) {
                    Toast.makeText(requireContext(), "Clicked on recently read story with ID: " + storyId, Toast.LENGTH_SHORT).show();
                }
            });
            binding.recyclerViewOffline.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewOffline.setAdapter(adapter);
        });

        viewModel.getFavoriteStories().observe(getViewLifecycleOwner(), stories -> {
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories, new StoryAdapter.OnStoryClickListener() {
                @Override
                public void onStoryClick(String storyId) {
                    Toast.makeText(requireContext(), "Clicked on favorite story with ID: " + storyId, Toast.LENGTH_SHORT).show();
                }
            });
            binding.recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewFavorites.setAdapter(adapter);
        });

        viewModel.getDownloadedStories().observe(getViewLifecycleOwner(), stories -> {
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories, new StoryAdapter.OnStoryClickListener() {
                @Override
                public void onStoryClick(String storyId) {
                    Toast.makeText(requireContext(), "Clicked on downloaded story with ID: " + storyId, Toast.LENGTH_SHORT).show();
                }
            });
            binding.recyclerViewDownloads.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewDownloads.setAdapter(adapter);
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}