package com.example.proreadapp.view.fragment;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.proreadapp.R;
import com.example.proreadapp.adapter.StoryAdapter;
import com.example.proreadapp.databinding.FragmentHomeBinding;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.repository.StoryRepository;
import com.example.proreadapp.view.ShowListActivity;
import com.example.proreadapp.view.StoryDetailActivity;
import com.example.proreadapp.viewmodel.HomeViewModel;
import com.example.proreadapp.viewmodel.HomeViewModelFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment{
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Application application = requireActivity().getApplication();
        StoryRepository repository = new StoryRepository(application);
        HomeViewModelFactory factory = new HomeViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        observerData();

        return binding.getRoot();
    }

    private void navigateToShowListActivity(String title, List<Story> storyList){
        List<String> storyIds = new ArrayList<>();
        for (Story story : storyList){
            storyIds.add(String.valueOf(story.getId()));
        }

        Intent intent = new Intent(requireContext(), ShowListActivity.class);
        intent.putExtra("title", title);
        intent.putStringArrayListExtra("storyIds", new ArrayList<>(storyIds));
        startActivity(intent);
    }

    private void observerData() {
        viewModel.getNewestStoryList().observe(getViewLifecycleOwner(), stories -> {
            for (Story story : stories) {
                Log.d("RoomCheck", "Title: " + story.getTitle() + ", Image URL: " + story.getImageUri());
            }
            if(stories != null){
                List<Story> reversed = new ArrayList<>(stories);
                Collections.reverse(reversed);
                stories = reversed;
            }
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories, new StoryAdapter.OnStoryClickListener() {
                @Override
                public void onStoryClick(String storyId) {Intent intent = new Intent(requireContext(), StoryDetailActivity.class);
                    intent.putExtra("storyId", storyId);
                    startActivity(intent);
                    Toast.makeText(requireContext(), "Clicked on story ID: " + storyId, Toast.LENGTH_SHORT).show();
                }
            });
            binding.recyclerViewNewest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewNewest.setAdapter(adapter);
        });

        viewModel.getRecentlyUpdatedStoryList().observe(getViewLifecycleOwner(), stories -> {
            if (stories != null) {
                StoryAdapter adapter = new StoryAdapter(requireContext(), stories, new StoryAdapter.OnStoryClickListener() {
                    @Override
                    public void onStoryClick(String storyId) {
                        Intent intent = new Intent(requireContext(), StoryDetailActivity.class);
                        intent.putExtra("storyId", storyId);
                        startActivity(intent);
                        Toast.makeText(requireContext(), "Clicked on story ID: " + storyId, Toast.LENGTH_SHORT).show();
                    }
                });
                binding.recyclerViewRecentlyUpdated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                binding.recyclerViewRecentlyUpdated.setAdapter(adapter);
            }
        });


        viewModel.getCompleteStoryList().observe(getViewLifecycleOwner(), stories -> {
            if(stories != null){
                List<Story> reversed = new ArrayList<>(stories);
                Collections.reverse(reversed);
                stories = reversed;
            }
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories, new StoryAdapter.OnStoryClickListener() {
                @Override
                public void onStoryClick(String storyId) {Intent intent = new Intent(requireContext(), StoryDetailActivity.class);
                    intent.putExtra("storyId", storyId);
                    startActivity(intent);
                    Toast.makeText(requireContext(), "Clicked on story ID: " + storyId, Toast.LENGTH_SHORT).show();
                }
            });
            binding.recyclerViewComplete.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewComplete.setAdapter(adapter);
        });

        viewModel.getLastReadStoryList().observe(getViewLifecycleOwner(), stories -> {
            if (stories != null) {
                List<Story> filteredStories = new ArrayList<>();
                for (Story s : stories) {
                    if (s.getLastReadAt() > 0) {
                        filteredStories.add(s);
                    }
                }

                StoryAdapter adapter = new StoryAdapter(requireContext(), filteredStories, new StoryAdapter.OnStoryClickListener() {
                    @Override
                    public void onStoryClick(String storyId) {
                        Intent intent = new Intent(requireContext(), StoryDetailActivity.class);
                        intent.putExtra("storyId", storyId);
                        startActivity(intent);
                        Toast.makeText(requireContext(), "Clicked on story ID: " + storyId, Toast.LENGTH_SHORT).show();
                    }
                });
                binding.recyclerViewLastRead.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                binding.recyclerViewLastRead.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
