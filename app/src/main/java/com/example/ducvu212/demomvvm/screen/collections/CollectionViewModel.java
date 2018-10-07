package com.example.ducvu212.demomvvm.screen.collections;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.widget.ProgressBar;
import com.example.ducvu212.demomvvm.BuildConfig;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.screen.base.BaseViewModel;
import com.example.ducvu212.demomvvm.screen.home.adapter.NewAdapter;
import com.example.ducvu212.demomvvm.utils.common.DisplayUtils;
import com.example.ducvu212.demomvvm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Exposes the data to be used in the Mvp screen.
 */

public class CollectionViewModel extends BaseViewModel implements LifecycleOwner {

    private Context mContext;
    private BaseSchedulerProvider mSchedulerProvider;
    private ImageRepository mRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LifecycleRegistry mLifecycleRegistry;
    private List<Image> mCollections;
    private NewAdapter mNewAdapter;
    private MutableLiveData mCollectionData;
    private ObservableField<NewAdapter> mCollectionObservableField = new ObservableField<>();
    private ProgressBar mProgressBar;
    private int mPageNumber = 1;
    private int mId;

    public CollectionViewModel(Context context, ImageRepository repository, FragmentManager manager,
            int id) {
        mContext = context;
        mRepository = repository;
        mId = id;
        mCollectionData = new MutableLiveData();
        mCollections = new ArrayList<>();
        mNewAdapter = new NewAdapter(mContext, manager);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    public ObservableField<NewAdapter> getCollectionObservableField() {
        return mCollectionObservableField;
    }

    private LiveData<List<Image>> getCollectionDetails(NewAdapter adapter, int page) {
        mCollectionObservableField.set(adapter);
        Disposable disposable = mRepository.getCollectionsDetails(mId, page, BuildConfig.API_KEY)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(images -> {
                    mCollections.addAll(images);
                    mCollectionData.setValue(mCollections);
                }, throwable -> {
                    mCollectionData.setValue(null);
                    DisplayUtils.makeToast(mContext, throwable.getMessage());
                });
        mCompositeDisposable.add(disposable);
        return mCollectionData;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    protected void onStart() {
        subscribeData();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    void OnCollectionLoadMore(ProgressBar progressBar) {
        mProgressBar = progressBar;
        mPageNumber++;
        getCollectionDetails(mNewAdapter, mPageNumber);
    }

    private void subscribeData() {
        getCollectionDetails(mNewAdapter, mPageNumber).observe(this, images -> {
            if (mProgressBar != null) {
                mProgressBar.setVisibility(GONE);
            }
            mNewAdapter.setNewImages(images);
            mNewAdapter.notifyDataSetChanged();
        });
    }
}
