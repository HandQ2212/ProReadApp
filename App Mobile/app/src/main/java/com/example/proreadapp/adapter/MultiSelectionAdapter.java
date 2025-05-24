package com.example.proreadapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proreadapp.databinding.ItemCategorySelectableBinding;
import com.example.proreadapp.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionAdapter.CategoryViewHolder> {

    private List<Category> categories = new ArrayList<>();
    private final List<Category> selectedCategories = new ArrayList<>();

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategorySelectableBinding binding = ItemCategorySelectableBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void submitList(List<Category> newCategories) {
        this.categories = newCategories;
        notifyDataSetChanged();
    }

    public void setSelectedCategories(List<Category> selected) {
        selectedCategories.clear();
        selectedCategories.addAll(selected);
        notifyDataSetChanged();
    }

    public List<Category> getSelectedCategories() {
        return new ArrayList<>(selectedCategories);
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategorySelectableBinding binding;

        public CategoryViewHolder(ItemCategorySelectableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                Category category = categories.get(getAdapterPosition());
                if (selectedCategories.contains(category)) {
                    selectedCategories.remove(category);
                } else {
                    selectedCategories.add(category);
                }
                notifyItemChanged(getAdapterPosition());
            });
        }

        void bind(Category category) {
            binding.textCategoryName.setText(category.getName());
            binding.cardCategory.setCardBackgroundColor(
                    selectedCategories.contains(category)
                            ? 0xFFE0E0E0
                            : 0xFFFFFFFF
            );
        }
    }
}
