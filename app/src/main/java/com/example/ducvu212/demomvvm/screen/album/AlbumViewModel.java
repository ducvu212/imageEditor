package com.example.ducvu212.demomvvm.screen.album;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import com.example.ducvu212.demomvvm.data.model.Album;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.screen.base.BaseViewModel;
import com.example.ducvu212.demomvvm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;

public class AlbumViewModel extends BaseViewModel implements LifecycleOwner {
    private BaseSchedulerProvider mSchedulerProvider;
    private ImageRepository mRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LifecycleRegistry mLifecycleRegistry;
    private AlbumAdapter mAlbumAdapter;
    private MutableLiveData mAlbumData;
    private String mAlbumName;
    private ObservableField<AlbumAdapter> mAlbumObservableField = new ObservableField<>();

    public AlbumViewModel(Context context, FragmentManager manager, ImageRepository repository, String albumName) {
        mRepository = repository;
        mAlbumName = albumName;
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        mAlbumData = new MutableLiveData();
        mAlbumAdapter =  new AlbumAdapter(context, manager);
    }

    @Override
    protected void onStart() {
        subcribeData(mAlbumName);
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    public ObservableField<AlbumAdapter> getLibraryObservableField() {
        return mAlbumObservableField;
    }

    public void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    private LiveData<Album> getAlbumFromLocal(AlbumAdapter albumAdapter, String albumName) {
        mAlbumObservableField.set(albumAdapter);
        Disposable disposable = mRepository.getAlbumFromLocal(albumName)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(album -> {
                    mAlbumData.setValue(album);
                }, throwable -> {
                    mAlbumData.setValue(null);
                });
        mCompositeDisposable.add(disposable);
        return mAlbumData;
    }

    public void subcribeData(String albumName) {
        getAlbumFromLocal(mAlbumAdapter, albumName).observe(this, album -> {
            List<Image> images = album.getImages();
            mAlbumAdapter.setAdapter(images);
            mAlbumAdapter.notifyDataSetChanged();
        });
    }
}
