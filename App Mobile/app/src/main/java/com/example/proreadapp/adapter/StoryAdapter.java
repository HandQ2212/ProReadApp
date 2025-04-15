package com.example.proreadapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proreadapp.databinding.ItemStoryBinding;
import com.example.proreadapp.model.Story;
import com.example.proreadapp.view.StoryDetailActivity;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
    private final Context context;
    private final List<Story> storyList;

    public StoryAdapter(Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
    }
    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemStoryBinding binding = ItemStoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new StoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder( StoryViewHolder holder, int position) {
        Story story = storyList.get(position);
        holder.binding.textTitle.setText(story.getTitle());
        holder.binding.imageCover.setImageResource(story.getImageResId());

        holder.binding.imageCover.setOnClickListener(v -> {
            Intent intent = new Intent(context, StoryDetailActivity.class);
            intent.putExtra("title", story.getTitle());
            intent.putExtra("author", story.getAuthor());
            intent.putExtra("info", story.getInfo());
            intent.putExtra("description", story.getDescription());
            intent.putExtra("imageResId", story.getImageResId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        final ItemStoryBinding binding;

        public StoryViewHolder(ItemStoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
