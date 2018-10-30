package com.example.ducvu212.demomvvm.screen.album;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.data.source.local.ImageDatabase;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.databinding.FragmentAlbumBinding;
import com.example.ducvu212.demomvvm.screen.base.BaseFragment;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;

public class AlbumFragment extends BaseFragment {
    public static final String ARGUMENT_ALBUM_NAME = "ARGUMENT_ALBUM_NAME";
    public static final String TAG = AlbumFragment.class.getSimpleName();
    private FragmentActivity mContext;
    private AlbumViewModel mViewModel;
    private String mAlbumName;

    public static AlbumFragment newInstance(String albumName) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_ALBUM_NAME, albumName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlbumName = getArguments().getString(ARGUMENT_ALBUM_NAME);
        }
        initBinding();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FragmentAlbumBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }

    private void initBinding() {
        ImageDatabase database = ImageDatabase.getInstance(mContext);
        mViewModel = new AlbumViewModel(mContext, mContext.getSupportFragmentManager(),
                ImageRepository.getsInstance(ImageRemoteDataSource.getsInstance(),
                        ImageLocalDataSource.getsInstance(database.mImageDAO(), mContext)), mAlbumName);
        mViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
        if (((AppCompatActivity) mContext).getSupportActionBar() != null) {
            ((AppCompatActivity) mContext).getSupportActionBar().setTitle(mAlbumName);
        }
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
