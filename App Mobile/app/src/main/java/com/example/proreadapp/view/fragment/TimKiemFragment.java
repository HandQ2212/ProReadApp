package com.example.proreadapp.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.proreadapp.databinding.FragmentTimKiemBinding;
import com.example.proreadapp.model.SearchItem;
import com.example.proreadapp.adapter.SearchAdapter;
import com.example.proreadapp.viewmodel.SearchViewModel;

import java.util.List;

public class TimKiemFragment extends Fragment {
    private FragmentTimKiemBinding binding;
    private SearchViewModel viewModel;
    private SearchAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTimKiemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModel();
        setupRecyclerView();
        setupSearchView();
        observeViewModel();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new SearchAdapter(requireContext(), this::onSearchItemClick);
        binding.rvSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvSearchResults.setAdapter(adapter);
    }

    private void setupSearchView() {
        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(v.getText().toString());
                return true;
            }
            return false;
        });

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.imgClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                viewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.imgClear.setOnClickListener(v -> {
            binding.edtSearch.setText("");
            binding.imgClear.setVisibility(View.GONE);
        });

        binding.imgSearch.setOnClickListener(v -> performSearch(binding.edtSearch.getText().toString()));
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
        });
    }

    private void performSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            return;
        }

        viewModel.searchBooks(query).observe(getViewLifecycleOwner(), this::handleSearchResults);
    }

    private void handleSearchResults(List<SearchItem> results) {
        if (results.isEmpty()) {
            binding.tvNoResults.setVisibility(View.VISIBLE);
            binding.rvSearchResults.setVisibility(View.GONE);
        } else {
            binding.tvNoResults.setVisibility(View.GONE);
            binding.rvSearchResults.setVisibility(View.VISIBLE);
            adapter.setItems(results);
        }
    }

    private void onSearchItemClick(SearchItem item) {
        Toast.makeText(requireContext(), "Đã chọn: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}