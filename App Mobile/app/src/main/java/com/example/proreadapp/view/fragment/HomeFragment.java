package com.example.proreadapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.proreadapp.R;
import com.example.proreadapp.adapter.StoryAdapter;
import com.example.proreadapp.databinding.FragmentHomeBinding;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.view.ShowListActivity;
import com.example.proreadapp.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(HomeViewModel.class);

        observeData();
        setupClickListeners();

        return binding.getRoot();
    }

    private void observeData() {
        // Quan sát và hiển thị danh sách stories
        viewModel.getNewestStories().observe(getViewLifecycleOwner(), stories -> {
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories);
            binding.recyclerViewNewest.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewNewest.setAdapter(adapter);
        });

        viewModel.getRecentlyUpdatedStories().observe(getViewLifecycleOwner(), stories -> {
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories);
            binding.recyclerViewRecentlyUpdated.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewRecentlyUpdated.setAdapter(adapter);
        });

        viewModel.getCompleteStories().observe(getViewLifecycleOwner(), stories -> {
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories);
            binding.recyclerViewComplete.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewComplete.setAdapter(adapter);
        });
    }

    private void setupClickListeners() {
        binding.layoutFavorite.setOnClickListener(v -> {
            viewModel.getFavoriteStoryIds().observe(getViewLifecycleOwner(), storyIds -> {
                navigateToShowListActivity("Yêu Thích", storyIds);
            });
        });

        binding.layoutMostView.setOnClickListener(v -> {
            viewModel.getMostViewStoryIds().observe(getViewLifecycleOwner(), storyIds -> {
                navigateToShowListActivity("Xem Nhiều", storyIds);
            });
        });

        binding.layoutTrending.setOnClickListener(v -> {
            viewModel.getTrendingStoryIds().observe(getViewLifecycleOwner(), storyIds -> {
                navigateToShowListActivity("Thịnh Hành", storyIds);
            });
        });
    }

    private void navigateToShowListActivity(String title, List<String> storyIds) {
        Intent intent = new Intent(requireContext(), ShowListActivity.class);
        intent.putExtra("title", title);
        intent.putStringArrayListExtra("storyIds", new ArrayList<>(storyIds));
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}