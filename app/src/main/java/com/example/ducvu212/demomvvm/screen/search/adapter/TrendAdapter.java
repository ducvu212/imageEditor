package com.example.ducvu212.demomvvm.screen.search.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.databinding.ItemTrendSearchBinding;
import com.example.ducvu212.demomvvm.screen.search.HandleClick;
import java.util.ArrayList;
import java.util.List;

public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.TrendViewHolder> {
    private List<String> mTrends;

    public TrendAdapter () {
        mTrends = new ArrayList<>();
    }

    public void setTrends(List<String> trends) {
        mTrends.clear();
        mTrends.addAll(trends);
    }

    @NonNull
    @Override
    public TrendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTrendSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_trend_search, viewGroup, false);
        return new TrendViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendViewHolder trendViewHolder, int i) {
        trendViewHolder.binding(mTrends.get(i));
    }

    @Override
    public int getItemCount() {
        return mTrends == null ? 0 : mTrends.size() ;
    }

    static class TrendViewHolder extends RecyclerView.ViewHolder {
        private ItemTrendSearchBinding mBinding;

        public TrendViewHolder(@NonNull ItemTrendSearchBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void binding(String trend) {
            mBinding.setTrend(trend);
            mBinding.setListener(new HandleClick());
            mBinding.executePendingBindings();
        }
    }
}
