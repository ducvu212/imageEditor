package com.example.ducvu212.demomvvm.screen.editor;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.screen.base.BaseViewModel;
import com.example.ducvu212.demomvvm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import java.util.concurrent.ExecutionException;

/**
 * Created by CuD HniM on 18/10/10.
 */
public class EditorViewModel extends BaseViewModel implements LifecycleOwner {

    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LifecycleRegistry mLifecycleRegistry;
    private ImageRepository mRepository;

    public EditorViewModel(ImageRepository imageRepository) {
        mRepository = imageRepository;
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
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

    Bitmap convertBitmap(String url) throws ExecutionException, InterruptedException {
        return mRepository.getBitmap(url);
    }

    Bitmap convertbitmapGallary(String path) {
        return mRepository.getBitmapGallary(path);
    }
}
