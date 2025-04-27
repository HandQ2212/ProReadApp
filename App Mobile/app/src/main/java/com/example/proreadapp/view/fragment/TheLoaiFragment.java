package com.example.proreadapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.proreadapp.adapter.CategoryAdapter;
import com.example.proreadapp.databinding.FragmentTheLoaiBinding;
import com.example.proreadapp.viewmodel.CategoryViewModel;

public class TheLoaiFragment extends Fragment{

    private FragmentTheLoaiBinding binding;
    private CategoryViewModel viewModel;
    private CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentTheLoaiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        adapter = new CategoryAdapter(category ->{
            // Chuyển sang màn hình hiển thị danh sách truyện thuộc thể loại
            Toast.makeText(getContext(), "Chọn thể loại: " + category, Toast.LENGTH_SHORT).show();
        });

        binding.theLoaiRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.theLoaiRecyclerView.setAdapter(adapter);

        viewModel.getCategories().observe(getViewLifecycleOwner(), adapter::setCategories);

        if (viewModel.getCategories().getValue().isEmpty()){
            viewModel.addCategory("Ngôn Tình");
            viewModel.addCategory("Ngược");
            viewModel.addCategory("Huyền Huyễn");
            viewModel.addCategory("Khoa học");
            viewModel.addCategory("Xuyên Không");
            viewModel.addCategory("Niên Đại");
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
