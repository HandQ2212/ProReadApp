package com.example.proreadapp.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proreadapp.databinding.ActivityShowListBinding;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.viewmodel.ShowListViewModel;
import com.example.proreadapp.adapter.StoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowListActivity extends AppCompatActivity{
    private ActivityShowListBinding binding;
    private ShowListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding = ActivityShowListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ShowListViewModel.class);

        String title = getIntent().getStringExtra("title");
        binding.textTitle.setText(title);
        List<String> storyIds = getIntent().getStringArrayListExtra("storyIds");

        if (storyIds != null && !storyIds.isEmpty()){
            List<Story> storyList = fetchStoriesFromIds(storyIds);

            viewModel.setStoryList(storyList);

            viewModel.getStoryList().observe(this, stories ->{
                // Tạo adapter và gán cho RecyclerView
                StoryAdapter adapter = new StoryAdapter(this, stories);
                binding.recyclerView.setAdapter(adapter);
            });
        } else{
            Toast.makeText(this, "Không có truyện để hiển thị", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Story> fetchStoriesFromIds(List<String> storyIds){
        List<Story> stories = new ArrayList<>();
        for (String id : storyIds){
            // lay du lieu tu firebase = id
        }
        return stories;
    }
}
