package com.example.ducvu212.demomvvm.screen.details;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.data.source.local.ImageDatabase;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.databinding.FragmentImageDetailsBinding;
import com.example.ducvu212.demomvvm.screen.base.BaseFragment;
import com.example.ducvu212.demomvvm.screen.home.BindingHome;
import com.example.ducvu212.demomvvm.utils.common.DisplayUtils;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;

/**
 * ImageDetails Screen.
 */
public class ImageDetailsFragment extends BaseFragment implements ImageDetailsViewListener {

    public static final String TAG = ImageDetailsFragment.class.getSimpleName();
    private static final String ARGUMENT_IMAGE = "ARGUMENT_IMAGE";
    private FragmentActivity mContext;
    private ImageDetailsViewModel mViewModel;
    private FragmentImageDetailsBinding mBinding;
    private ActionBar mActionBar;
    private BroadcastReceiver mOnComplete;

    public static ImageDetailsFragment newInstance(ImageRandom imageRandom) {
        ImageDetailsFragment fragment = new ImageDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_IMAGE, imageRandom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageDatabase database = ImageDatabase.getInstance(mContext);
        mViewModel = new ImageDetailsViewModel(mContext,
                ImageRepository.getsInstance(ImageRemoteDataSource.getsInstance(),
                        ImageLocalDataSource.getsInstance(database.mImageDAO(), mContext)), this);
        mViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mActionBar = ((AppCompatActivity) mContext).getSupportActionBar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_details, container,
                false);
        mBinding.setViewModel(mViewModel);
        mBinding.setListener(new HandleImageClick(mContext, mViewModel));
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        if (getArguments() != null) {
            ImageRandom imageRandom = getArguments().getParcelable(ARGUMENT_IMAGE);
            ImageRandom imageCheck = mViewModel.getUserById(imageRandom);
            mBinding.setItem(imageRandom);
            if (imageCheck == null) {
                updateUI(imageRandom);
            } else {
                updateUI(imageCheck);
            }
        }
        mOnComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mBinding.imageViewDownload.setClickable(false);
                mViewModel.updateDownload();
                updateDownload(1);
            }
        };
        mContext.registerReceiver(mOnComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void updateUI(ImageRandom imageRandom) {
        String urlImage = imageRandom.getRawImage();
        updateLike(imageRandom.getLikeByUser());
        updateDownload(imageRandom.getDownloaded());
        BindingHome.loadImage(mBinding.imageViewContent, urlImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
        if (((AppCompatActivity) mContext).getSupportActionBar() != null) {
            ((AppCompatActivity) mContext).getSupportActionBar()
                    .setTitle(getResources().getString(R.string.title_images_details));
        }
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mContext.unregisterReceiver(mOnComplete);
        super.onDestroy();
    }

    @Override
    public void updateLikeButton(int like) {
        updateLike(like);
    }

    @Override
    public void updateDownloadButton(int downloaded) {
        updateDownload(downloaded);
    }

    @Override
    public void downloadStatus(String status) {
        DisplayUtils.makeToast(mContext, status);
    }

    private void updateLike(int like) {
        if (like == 1) {
            mBinding.imageViewLike.setImageResource(R.drawable.ic_like);
        } else {
            mBinding.imageViewLike.setImageResource(R.drawable.ic_un_like);
        }
    }

    private void updateDownload(int download) {
        if (download == 1) {
            mBinding.imageViewDownload.setImageResource(R.drawable.ic_downloaded);
        } else {
            mBinding.imageViewDownload.setImageResource(R.drawable.ic_download);
        }
    }
}
