package com.example.ducvu212.demomvvm.screen.filtter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SeekBar;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemFilter;
import com.example.ducvu212.demomvvm.databinding.ItemFilterBinding;
import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {
    private List<ItemFilter> mFilters;
    private OnUpdateUIFilter mOnUpdateUIFilter;

    public FilterAdapter(OnUpdateUIFilter onUpdateUIFilter) {
        mOnUpdateUIFilter = onUpdateUIFilter;
        mFilters = new ArrayList<>();
    }

    public void setFilters(List<ItemFilter> filters) {
        mFilters.clear();
        mFilters.addAll(filters);
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_filter, parent, false);
        return new FilterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        holder.binding(mFilters.get(position), mOnUpdateUIFilter);
    }

    @Override
    public int getItemCount() {
        return mFilters == null ? 0: mFilters.size();
    }

    static class FilterViewHolder extends RecyclerView.ViewHolder {
        private ItemFilterBinding mBinding;

        public FilterViewHolder(ItemFilterBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void binding(ItemFilter itemFilter, OnUpdateUIFilter onUpdateUIFilter) {
            mBinding.setItem(itemFilter);
            mBinding.setListener(new HandleClickItemFilter(onUpdateUIFilter));
            mBinding.executePendingBindings();
        }
    }
}
