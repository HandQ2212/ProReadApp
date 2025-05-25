package com.example.proreadapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proreadapp.R;
import com.example.proreadapp.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private List<Chapter> chapters = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter currentChapter = chapters.get(position);
        holder.textChapterTitle.setText(currentChapter.getTitle());
        holder.textChapterNumber.setText("Chapter " + currentChapter.getCurrentChapter() +
                "/" + currentChapter.getTotalChapters());
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView textChapterTitle;
        private TextView textChapterNumber;
        private ImageButton btnEditChapter;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textChapterTitle = itemView.findViewById(R.id.text_chapter_title);
            textChapterNumber = itemView.findViewById(R.id.text_chapter_number);
            btnEditChapter = itemView.findViewById(R.id.btn_edit_chapter);

            btnEditChapter.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(chapters.get(position));
                }
            });

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(chapters.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Chapter chapter);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}