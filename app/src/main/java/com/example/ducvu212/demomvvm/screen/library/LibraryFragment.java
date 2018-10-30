package com.example.ducvu212.demomvvm.screen.library;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.ducvu212.demomvvm.databinding.FragmentLibraryBinding;
import com.example.ducvu212.demomvvm.screen.base.BaseFragment;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;

public class LibraryFragment extends BaseFragment {
    public static final String TAG = LibraryFragment.class.getSimpleName();
    private static LibraryFragment sInstance;
    private FragmentActivity mContext;
    private LibraryViewModel mViewModel;
    private FragmentLibraryBinding mBinding;

    public static LibraryFragment newInstance() {
        if (sInstance == null) {
            sInstance = new LibraryFragment();
        }
        return sInstance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_library, container, false);
        initBinding();
        return mBinding.getRoot();
    }

    private void initBinding() {
        ImageDatabase database = ImageDatabase.getInstance(mContext);
        mViewModel = new LibraryViewModel(mContext, new ImageRepository(ImageRemoteDataSource.getsInstance(),
                ImageLocalDataSource.getsInstance(database.mImageDAO(), mContext)), mContext.getSupportFragmentManager());
        mViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
        if (((AppCompatActivity) mContext).getSupportActionBar() != null) {
            ((AppCompatActivity) mContext).getSupportActionBar().setTitle("Library");
        }
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
