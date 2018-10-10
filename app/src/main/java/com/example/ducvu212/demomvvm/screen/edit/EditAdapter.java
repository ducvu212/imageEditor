package com.example.ducvu212.demomvvm.screen.edit;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemEdit;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.databinding.ItemEditBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/10.
 */
public class EditAdapter extends RecyclerView.Adapter<EditAdapter.EditViewHolder> {

    private List<ItemEdit> mEditList;
    private SeekBar mSeekBar;
    private ImageRepository mImageRepository;
    private FrameLayout mFrameLayout;
    private OnUpdateUI mOnUpdateUI;

    public EditAdapter(List<ItemEdit> editList, ImageRepository repository, SeekBar seekBar,
            FrameLayout frameLayout, OnUpdateUI onUpdateUI) {
        mEditList = new ArrayList<>();
        mEditList.addAll(editList);
        mSeekBar = seekBar;
        mFrameLayout = frameLayout;
        mImageRepository = repository;
        mOnUpdateUI = onUpdateUI;
    }

    public void setEditList(List<ItemEdit> editList) {
        mEditList.addAll(editList.subList(mEditList.size(), editList.size()));
    }

    @NonNull
    @Override
    public EditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEditBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_edit, parent, false);
        return new EditViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EditViewHolder holder, int position) {
        holder.binding(mEditList.get(position), mImageRepository, mSeekBar, mFrameLayout,
                mOnUpdateUI);
    }

    @Override
    public int getItemCount() {
        return mEditList == null ? 0 : mEditList.size();
    }

    static class EditViewHolder extends RecyclerView.ViewHolder {

        private ItemEditBinding mBinding;

        EditViewHolder(ItemEditBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        void binding(ItemEdit edit, ImageRepository imageRepository, SeekBar seekBar,
                FrameLayout frameLayout, OnUpdateUI onUpdateUI) {
            mBinding.setItem(edit);
            mBinding.setListener(
                    new HandleItemEditClick(seekBar, imageRepository, frameLayout, onUpdateUI));
            mBinding.executePendingBindings();
        }
    }
}
