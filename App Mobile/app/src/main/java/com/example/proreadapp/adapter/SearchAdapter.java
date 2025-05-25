package com.example.proreadapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proreadapp.R;
import com.example.proreadapp.model.SearchItem;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final Context context;
    private List<SearchItem> searchItems;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SearchItem item);
    }

    public SearchAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.searchItems = new ArrayList<>();
        this.listener = listener;
    }

    public void setItems(List<SearchItem> items) {
        this.searchItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchItem item = searchItems.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgThumbnail;
        private final TextView tvTitle;
        private final TextView tvAuthor;
        private final TextView tvSummary;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvSummary = itemView.findViewById(R.id.tvSummary);
        }

        public void bind(final SearchItem item, final OnItemClickListener listener) {
            tvTitle.setText(item.getTitle());
            tvAuthor.setText(item.getAuthor());
            tvSummary.setText(item.getSummary());

            if (item.getThumbnailUrl() != null && !item.getThumbnailUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(item.getThumbnailUrl())
                        .placeholder(R.drawable.ic_image_placeholder)
                        .error(R.drawable.error_book)
                        .into(imgThumbnail);
            } else {
                imgThumbnail.setImageResource(R.drawable.ic_image_placeholder);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}