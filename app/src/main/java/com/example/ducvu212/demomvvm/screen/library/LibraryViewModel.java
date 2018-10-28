package com.example.ducvu212.demomvvm.screen.library;

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
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.screen.base.BaseViewModel;
import com.example.ducvu212.demomvvm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

public class LibraryViewModel extends BaseViewModel implements LifecycleOwner {
    private Context mContext;
    private BaseSchedulerProvider mSchedulerProvider;
    private ImageRepository mRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LifecycleRegistry mLifecycleRegistry;
    private List<Album> mAlbums;
    private LibraryAdapter mLibraryAdapter;
    private MutableLiveData mLibraryData;private ObservableField<LibraryAdapter> mLibraryObservableField = new ObservableField<>();

    public LibraryViewModel(Context context, ImageRepository repository, FragmentManager manager) {
        mContext = context;
        mRepository = repository;
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        mLibraryData = new MutableLiveData();
        mAlbums = new ArrayList<>();
        mLibraryAdapter =  new LibraryAdapter(manager);
    }

    @Override
    protected void onStart() {
        subcribeData();
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

    public ObservableField<LibraryAdapter> getLibraryObservableField() {
        return mLibraryObservableField;
    }

    public void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    private LiveData<List<Album>> getAlbumFromLocal(LibraryAdapter libraryAdapter) {
        mLibraryObservableField.set(libraryAdapter);
        Disposable disposable = mRepository.getCollectionsFromLoacl()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(albums -> {
                   mAlbums.addAll(albums);
                   mLibraryData.setValue(mAlbums);
                }, throwable -> {
                    mLibraryData.setValue(null);
                });
        mCompositeDisposable.add(disposable);
        return mLibraryData;
    }

    public void subcribeData() {
        getAlbumFromLocal(mLibraryAdapter).observe(this, albums ->  {
            mLibraryAdapter.setAlbums(albums);
            mLibraryAdapter.notifyDataSetChanged();
        });
    }
}
