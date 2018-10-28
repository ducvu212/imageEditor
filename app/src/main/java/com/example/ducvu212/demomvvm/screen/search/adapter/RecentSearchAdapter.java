package com.example.ducvu212.demomvvm.screen.search.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.RecentSearch;
import com.example.ducvu212.demomvvm.databinding.ItemRecentSearchBinding;
import com.example.ducvu212.demomvvm.screen.search.HandleClick;
import java.util.ArrayList;
import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.RecentHolder> {
    private List<RecentSearch> mSearchResponds;

    public RecentSearchAdapter() {
        mSearchResponds = new ArrayList<>();
    }

    public void setRecentSearch(List<RecentSearch> recentSearch) {
        mSearchResponds.clear();
        mSearchResponds.addAll(recentSearch);
    }

    @NonNull
    @Override
    public RecentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRecentSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_recent_search, viewGroup, false);
        return new RecentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentHolder recentHolder, int i) {
        recentHolder.binding(mSearchResponds.get(i));
    }

    @Override
    public int getItemCount() {
        return mSearchResponds == null ? 0 : mSearchResponds.size();
    }

    static class RecentHolder extends RecyclerView.ViewHolder {
        private ItemRecentSearchBinding mBinding;

        public RecentHolder(@NonNull ItemRecentSearchBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void binding(RecentSearch recentSearch) {
            mBinding.setRecent(recentSearch);
            mBinding.setListener(new HandleClick());
            mBinding.executePendingBindings();
        }
    }
}
