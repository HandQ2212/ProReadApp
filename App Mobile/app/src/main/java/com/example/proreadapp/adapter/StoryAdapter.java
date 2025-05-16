package com.example.proreadapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proreadapp.R;
import com.example.proreadapp.model.Story;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private final Context context;
    private final List<Story> storyList;
    private final OnStoryClickListener listener;

    public interface OnStoryClickListener {
        void onStoryClick(String storyId);
    }

    public StoryAdapter(Context context, List<Story> storyList, OnStoryClickListener listener) {
        this.context = context;
        this.storyList = storyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Story story = storyList.get(position);

        holder.textTitle.setText(story.getTitle());
        holder.imageStory.setImageResource(story.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onStoryClick(story.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return storyList != null ? storyList.size() : 0;
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageStory;
        TextView textTitle;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageStory = itemView.findViewById(R.id.imageStory);
            textTitle = itemView.findViewById(R.id.textTitle);
        }
    }
}