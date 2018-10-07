package com.example.ducvu212.demomvvm.screen.details;

import com.example.ducvu212.demomvvm.data.model.ItemViewPager;

/**
 * Created by CuD HniM on 18/10/06.
 */
public class HandleImageClick {

    private ImageDetailsViewModel mViewModel;

    public HandleImageClick(ImageDetailsViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void OnLikeClickListener(ItemViewPager itemViewPager) {
        //        if (itemViewPager.isLikeByUser()) {
        //            mViewModel.deleteImage(itemViewPager);
        //        } else {
        //            mViewModel.insertImage(itemViewPager);
        //        }
    }

    public void OnDownloadClickListener(ItemViewPager itemViewPager) {
        mViewModel.download(itemViewPager);
    }
}
