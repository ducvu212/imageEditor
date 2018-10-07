package com.example.ducvu212.demomvvm.screen.home.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.Collection;
import com.example.ducvu212.demomvvm.databinding.ItemColectionBinding;
import com.example.ducvu212.demomvvm.screen.home.HandleItemClick;
import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionHolder> {

    private Context mContext;
    private FragmentManager mManager;
    private List<Collection> mCollections;

    public CollectionAdapter(Context context, FragmentManager manager) {
        mContext = context;
        mManager = manager;
        mCollections = new ArrayList<>();
    }

    public void setCollections(List<Collection> collections) {
        mCollections.addAll(collections.subList(mCollections.size(), collections.size()));
    }

    @NonNull
    @Override
    public CollectionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemColectionBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_colection, viewGroup, false);
        return new CollectionHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionHolder collectionHolder, int i) {
        collectionHolder.binding(mContext, mManager, mCollections.get(i));
    }

    @Override
    public int getItemCount() {
        return mCollections == null ? 0 : mCollections.size();
    }

    static class CollectionHolder extends RecyclerView.ViewHolder {
        private ItemColectionBinding mBinding;

        CollectionHolder(@NonNull ItemColectionBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void binding(Context context, FragmentManager manager, Collection collection) {
            mBinding.setCollection(collection);
            mBinding.setListener(new HandleItemClick(context, manager));
            mBinding.executePendingBindings();
        }
    }
}
