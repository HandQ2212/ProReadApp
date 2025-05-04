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

public class HomeFragment extends Fragment{
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private List<Story> favoriteStoryList;
    private List<Story> mostViewStoryList;
    private List<Story> trendingStoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        initData();
        obseverData();

        binding.layoutFavorite.setOnClickListener(v ->{
            navigateToShowListActivity("Yêu Thích", favoriteStoryList);
        });
        binding.layoutMostView.setOnClickListener(v ->{
            navigateToShowListActivity("Xem Nhiều", mostViewStoryList);
        });
        binding.layoutTrending.setOnClickListener(v ->{
            navigateToShowListActivity("Thịnh Hành", trendingStoryList);
        });

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

    private void initData(){
        favoriteStoryList = new ArrayList<>();
        Story s1 = new Story("Tiêu đề 1", "Tác giả", "Thông tin", "Mô tả", R.drawable.mucthanky1618392290);
        s1.setId("101");
        Story s2 = new Story("Tiêu đề 2", "Tác giả 2", "Thông tin 2", "Mô tả 2", R.drawable.mucthanky1618392290);
        s2.setId("102");
        favoriteStoryList.add(s1);
        favoriteStoryList.add(s2);

        mostViewStoryList = new ArrayList<>();
        Story s3 = new Story("Tiêu đề 3", "Tác giả 3", "Thông tin 3", "Mô tả 3", R.drawable.mucthanky1618392290);
        s3.setId("201");
        Story s4 = new Story("Tiêu đề 4", "Tác giả 4", "Thông tin 4", "Mô tả 4", R.drawable.mucthanky1618392290);
        s4.setId("202");
        mostViewStoryList.add(s3);
        mostViewStoryList.add(s4);

        trendingStoryList = new ArrayList<>();
        Story s5 = new Story("Tiêu đề 5", "Tác giả 5", "Thông tin 5", "Mô tả 5", R.drawable.mucthanky1618392290);
        s5.setId("301");
        Story s6 = new Story("Tiêu đề 6", "Tác giả 6", "Thông tin 6", "Mô tả 6", R.drawable.mucthanky1618392290);
        s6.setId("302");
        trendingStoryList.add(s5);
        trendingStoryList.add(s6);
    }

    private void obseverData(){
        viewModel.getNewestStoryList().observe(getViewLifecycleOwner(), stories ->{
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories);
            binding.recyclerViewNewest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewNewest.setAdapter(adapter);
        });
        viewModel.getRecentlyUpdatedStoryList().observe(getViewLifecycleOwner(), stories ->{
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories);
            binding.recyclerViewRecentlyUpdated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewRecentlyUpdated.setAdapter(adapter);
        });
        viewModel.getCompleteStoryList().observe(getViewLifecycleOwner(), stories ->{
            StoryAdapter adapter = new StoryAdapter(requireContext(), stories);
            binding.recyclerViewComplete.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerViewComplete.setAdapter(adapter);
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
