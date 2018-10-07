package com.example.ducvu212.demomvvm.screen.details;

import android.app.DownloadManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.support.annotation.NonNull;
import com.example.ducvu212.demomvvm.data.model.ItemViewPager;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.screen.base.BaseViewModel;
import com.example.ducvu212.demomvvm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Exposes the data to be used in the ImageDetails screen.
 */

public class ImageDetailsViewModel extends BaseViewModel implements LifecycleOwner {

    private Context mContext;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LifecycleRegistry mLifecycleRegistry;
    private ImageRepository mRepository;
    private ImageDetailsViewListener mListener;

    ImageDetailsViewModel(Context context, ImageRepository repository,
            ImageDetailsViewListener imageDetailsViewListener) {
        mContext = context;
        mRepository = repository;
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        mListener = imageDetailsViewListener;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    protected void onStart() {
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    //    void insertImage(ItemViewPager itemViewPager) {
    //        Disposable disposable = Observable.create(emitter -> {
    //            mRepository.insertImage(itemViewPager);
    //            emitter.onComplete();
    //        })
    //                .subscribeOn(mSchedulerProvider.io())
    //                .observeOn(mSchedulerProvider.ui())
    //                .subscribe(o -> mListener.updateLikeButton(true),
    //                        throwable -> DisplayUtils.makeToast(mContext, throwable.getMessage
    // ()));
    //        mCompositeDisposable.add(disposable);
    //    }
    //
    //    void deleteImage(ItemViewPager itemViewPager) {
    //        Disposable disposable = Observable.create(emitter -> {
    //            mRepository.deleteImage(itemViewPager);
    //            emitter.onComplete();
    //        })
    //                .subscribeOn(mSchedulerProvider.io())
    //                .observeOn(mSchedulerProvider.ui())
    //                .subscribe(o -> mListener.updateLikeButton(false),
    //                        throwable -> DisplayUtils.makeToast(mContext, throwable.getMessage
    // ()));
    //        mCompositeDisposable.add(disposable);
    //    }

    void download(ItemViewPager itemViewPager) {
        mRepository.dowloadImage(
                (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE),
                itemViewPager.getPath(), itemViewPager.getImageId(), mListener);
    }
}
