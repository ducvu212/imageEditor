package com.example.ducvu212.demomvvm.screen.details;

import android.content.Context;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.screen.editor.EditActivity;

/**
 * Created by CuD HniM on 18/10/06.
 */
public class HandleImageClick {

    private Context mContext;
    private ImageDetailsViewModel mViewModel;

    public HandleImageClick(Context context, ImageDetailsViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;
    }

    public void OnLikeClickListener(ImageRandom imageRandom) {
        if (imageRandom.getLikeByUser() == 1) {
            imageRandom.setLikeByUser(0);
            mViewModel.updateImageLike(imageRandom);
        } else {
            imageRandom.setLikeByUser(1);
            mViewModel.updateImageLike(imageRandom);
        }
    }

    public void OnDownloadClickListener(ImageRandom imageRandom) {
        mViewModel.download(imageRandom);
    }

    public void OnEditClickListener(ImageRandom imageRandom) {
        mContext.startActivity(EditActivity.getProfileIntent(mContext, imageRandom));
    }
}
