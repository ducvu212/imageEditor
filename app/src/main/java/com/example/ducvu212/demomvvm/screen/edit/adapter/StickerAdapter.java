package com.example.ducvu212.demomvvm.screen.edit.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemSticker;
import com.example.ducvu212.demomvvm.databinding.ItemStickerBinding;
import com.example.ducvu212.demomvvm.screen.edit.HandleItemEditClick;
import com.example.ducvu212.demomvvm.screen.edit.OnUpdateUI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/24.
 */
public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerViewHolder> {

    private List<ItemSticker> mStickerList;
    private OnUpdateUI mOnUpdateUi;

    public StickerAdapter(OnUpdateUI onUpdateUI) {
        mStickerList = new ArrayList<>();
        mOnUpdateUi = onUpdateUI;
    }

    public void setStickerList(List<ItemSticker> stickerList) {
        mStickerList = stickerList;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStickerBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_sticker, parent, false);
        return new StickerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        holder.binding(mStickerList.get(position), mOnUpdateUi);
    }

    @Override
    public int getItemCount() {
        return mStickerList == null ? 0 : mStickerList.size();
    }

    static class StickerViewHolder extends RecyclerView.ViewHolder {

        private ItemStickerBinding mBinding;

        StickerViewHolder(ItemStickerBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        void binding(ItemSticker itemSticker, OnUpdateUI onUpdateUi) {
            mBinding.setItem(itemSticker);
            mBinding.setListener(new HandleItemEditClick(onUpdateUi));
            mBinding.executePendingBindings();
        }
    }
}
