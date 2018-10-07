package com.example.ducvu212.demomvvm.screen.home.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.model.ItemViewPager;
import com.example.ducvu212.demomvvm.databinding.ItemRandomImageBinding;
import com.example.ducvu212.demomvvm.screen.home.HandleItemClick;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/04.
 */
public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewHolder> {

    private Context mContext;
    private List<Image> mNewImages;
    private FragmentManager mManager;

    public NewAdapter(Context context, FragmentManager manager) {
        mContext = context.getApplicationContext();
        mManager = manager;
        mNewImages = new ArrayList<>();
    }

    public void setNewImages(List<Image> newImages) {
        mNewImages.addAll(newImages.subList(mNewImages.size(), newImages.size()));
    }

    @NonNull
    @Override
    public NewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRandomImageBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_random_image, viewGroup, false);
        return new NewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewHolder newHolder, int i) {
        newHolder.binding(mContext, mManager, mNewImages.get(i));
    }

    @Override
    public int getItemCount() {
        return mNewImages == null ? 0 : mNewImages.size();
    }

    static class NewHolder extends RecyclerView.ViewHolder {

        private ItemRandomImageBinding mBinding;

        NewHolder(@NonNull ItemRandomImageBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        void binding(Context context, FragmentManager manager, Image image) {
            mBinding.setItem(new ItemViewPager.Builder().mPath(image.getUrls().getRegular())
                    .mLikeByUser(image.getLikedByUser())
                    .mUserName(image.getUser().getUsername())
                    .mImageId(image.getId())
                    .mRawImage(image.getUrls().getRaw())
                    .build());
            mBinding.setListener(new HandleItemClick(context, manager));
            mBinding.executePendingBindings();
        }
    }
}
