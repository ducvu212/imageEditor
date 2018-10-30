package com.example.ducvu212.demomvvm.screen.album;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.data.model.ImageType;
import com.example.ducvu212.demomvvm.databinding.ItemAlbumBinding;
import com.example.ducvu212.demomvvm.screen.home.HandleItemClick;
import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    private Context mContext;
    private FragmentManager mManager;
    private List<Image> mImages;

    AlbumAdapter(Context context, FragmentManager manager) {
        mContext = context;
        mManager = manager;
        mImages = new ArrayList<>();
    }

    public void setAdapter(List<Image> images) {
        mImages.clear();
        mImages.addAll(images);
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemAlbumBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_album, viewGroup, false);
        return new AlbumHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder albumHolder, int i) {
        albumHolder.binding(mContext, mManager, mImages.get(i));
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    static class AlbumHolder extends RecyclerView.ViewHolder {
        private ItemAlbumBinding mBinding;
        AlbumHolder(@NonNull ItemAlbumBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void binding(Context context, FragmentManager manager, Image image) {
            mBinding.setItem(new ImageRandom.Builder().mRawImage(image.getPath())
                    .mPath(image.getPath())
                    .mType(ImageType.LOCAL)
                    .build());
            mBinding.setListener(new HandleItemClick(context, manager));
            mBinding.executePendingBindings();
        }
    }
}
