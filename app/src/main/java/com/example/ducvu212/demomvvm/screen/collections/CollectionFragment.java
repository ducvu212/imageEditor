package com.example.ducvu212.demomvvm.screen.collections;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.data.source.local.ImageDatabase;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.databinding.FragmentCollectionBinding;
import com.example.ducvu212.demomvvm.screen.EndlessScrollListener;
import com.example.ducvu212.demomvvm.screen.base.BaseFragment;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;

public class CollectionFragment extends BaseFragment {

    public static final String TAG = CollectionFragment.class.getSimpleName();
    private static final String ARGUMENT_COLLECTION_ID = "ARGUMENT_COLLECTION_ID";
    private static final String ARGUMENT_COLLECTION_NAME = "ARGUMENT_COLLECTION_NAME";
    private FragmentActivity mContext;
    private CollectionViewModel mViewModel;
    private FragmentCollectionBinding mBinding;
    private int mCollectionId;
    private String mCollectionName;

    public static CollectionFragment newInstance(int id, String name) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_COLLECTION_ID, id);
        args.putString(ARGUMENT_COLLECTION_NAME, name);
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
        if (getArguments() != null) {
            mCollectionId = getArguments().getInt(ARGUMENT_COLLECTION_ID);
            mCollectionName = getArguments().getString(ARGUMENT_COLLECTION_NAME);
        }
        ImageDatabase database = ImageDatabase.getInstance(mContext);
        mViewModel = new CollectionViewModel(mContext,
                ImageRepository.getsInstance(ImageRemoteDataSource.getsInstance(),
                        ImageLocalDataSource.getsInstance(database.mImageDAO(), mContext)),
                mContext.getSupportFragmentManager(), mCollectionId);
        mViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_collection, container, false);
        mBinding.setViewModel(mViewModel);
        mBinding.recyclerCollection.addOnScrollListener(new EndlessScrollListener(
                (LinearLayoutManager) mBinding.recyclerCollection.getLayoutManager()) {
            @Override
            protected void OnLoadMore() {
                mViewModel.OnCollectionLoadMore(mBinding.progressBarCollection);
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
        if (((AppCompatActivity) mContext).getSupportActionBar() != null) {
            ((AppCompatActivity) mContext).getSupportActionBar().setTitle(mCollectionName);
        }
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
