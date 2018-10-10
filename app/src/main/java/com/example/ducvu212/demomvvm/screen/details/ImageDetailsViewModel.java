package com.example.ducvu212.demomvvm.screen.details;

import android.app.DownloadManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.support.annotation.NonNull;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.screen.base.BaseViewModel;
import com.example.ducvu212.demomvvm.utils.common.DisplayUtils;
import com.example.ducvu212.demomvvm.utils.rx.BaseSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the ImageDetails screen.
 */

public class ImageDetailsViewModel extends BaseViewModel implements LifecycleOwner {

    private Context mContext;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LifecycleRegistry mLifecycleRegistry;
    private ImageRepository mRepository;
    private List<ImageRandom> mRandomList;
    private ImageRandom mImageRandomDownload;
    private ImageDetailsViewListener mListener;
    private ImageRandom mImageRandom;

    ImageDetailsViewModel(Context context, ImageRepository repository,
            ImageDetailsViewListener imageDetailsViewListener) {
        mContext = context;
        mRepository = repository;
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        mRandomList = new ArrayList<>();
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

    void updateImageLike(ImageRandom imageRandom) {
        Disposable disposable = Observable.create(emitter -> {
            mRepository.updateImage(imageRandom);
            emitter.onComplete();
        }).subscribeOn(mSchedulerProvider.io()).observeOn(mSchedulerProvider.ui()).subscribe(o -> {

                }, throwable -> DisplayUtils.makeToast(mContext, throwable.getMessage()),
                () -> mListener.updateLikeButton(imageRandom.getLikeByUser()));
        mCompositeDisposable.add(disposable);
    }

    void updateDownload() {
        Disposable disposable = Observable.create(emitter -> {
            mRepository.updateDownload(mImageRandomDownload);
            emitter.onComplete();
        }).subscribeOn(mSchedulerProvider.io()).observeOn(mSchedulerProvider.ui()).subscribe(o -> {
            mImageRandomDownload.setDownloaded(1);
        }, throwable -> DisplayUtils.makeToast(mContext, throwable.getMessage()), () -> {
            mListener.updateDownloadButton(mImageRandomDownload.getDownloaded());
            mImageRandomDownload.setDownloaded(1);
        });
        mCompositeDisposable.add(disposable);
    }

    void deleteImage(ImageRandom imageRandom) {
        Disposable disposable = Observable.create(emitter -> {
            mRepository.deleteImage(imageRandom);
            emitter.onComplete();
        })
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(o -> mListener.updateLikeButton(0),
                        throwable -> DisplayUtils.makeToast(mContext, throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    void download(ImageRandom imageRandom) {
        mImageRandomDownload = imageRandom;
        mImageRandomDownload.setDownloaded(1);
        mListener.updateDownloadButton(1);
        mRepository.dowloadImage(
                (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE),
                imageRandom.getPath(), imageRandom.getImageId(), mListener);
    }

    public ImageRandom getUserById(ImageRandom imageRandom) {
        mImageRandom = null;
        /*Disposable disposable = Observable.create(emitter -> {
            mImageRandom = mRepository.getImageById(imageRandom.getImageId());
            emitter.onComplete();
        }).subscribeOn(mSchedulerProvider.io()).observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, throwable -> {

                }, () -> {

                });
//        if (mImageRandom == null) {
//            insertImage(imageRandom);
//        }
//        try {
//            Log.d("TAGGGGG", mImageRandom.getLikeByUser() + "\n");
//        } catch (Exception e){
//            Log.d("TAGGGGG", e.getMessage() + "\n");
//        };
        mCompositeDisposable.add(disposable);*/

        mImageRandom = mRepository.getImageById("'" + imageRandom.getImageId() + "'");
        return mImageRandom;
    }

    void insertImage(ImageRandom imageRandom) {
        Disposable disposable = Observable.create(emitter -> {
            mRepository.insertImage(imageRandom);
            emitter.onComplete();
        }).subscribeOn(mSchedulerProvider.io()).observeOn(mSchedulerProvider.ui()).subscribe(o -> {

        }, throwable -> DisplayUtils.makeToast(mContext, throwable.getMessage()), () -> {

        });
        mCompositeDisposable.add(disposable);
    }
}
