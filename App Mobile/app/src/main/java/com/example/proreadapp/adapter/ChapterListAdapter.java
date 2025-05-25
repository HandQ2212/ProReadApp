package com.example.proreadapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proreadapp.databinding.ItemChapterBinding;
import com.example.proreadapp.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ChapterViewHolder> {

    public interface OnChapterClickListener {
        void onEditClick(Chapter chapter);
    }

    private List<Chapter> chapterList = new ArrayList<>();
    private final OnChapterClickListener listener;

    public ChapterListAdapter(OnChapterClickListener listener) {
        this.listener = listener;
    }

    public void setChapterList(List<Chapter> chapters) {
        this.chapterList = chapters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemChapterBinding binding = ItemChapterBinding.inflate(inflater, parent, false);
        return new ChapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.bind(chapter);
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {
        private final ItemChapterBinding binding;

        public ChapterViewHolder(ItemChapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Chapter chapter) {
            binding.textChapterTitle.setText(chapter.getTitle());

            binding.btnEditChapter.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(chapter);
                }
            });
        }
    }
}
