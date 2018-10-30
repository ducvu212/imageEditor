package com.example.ducvu212.demomvvm.screen.filtter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.ducvu212.demomvvm.data.model.ItemFilter;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.screen.base.BaseViewModel;
import com.example.ducvu212.demomvvm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;

public class FilterViewModel extends BaseViewModel implements LifecycleOwner{
    private BaseSchedulerProvider mSchedulerProvider;
    private ImageRepository mRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LifecycleRegistry mLifecycleRegistry;
    private FilterAdapter mFilterAdapter;
    private MutableLiveData mFilerData;
    private Bitmap mPath;
    private ObservableField<FilterAdapter> mFilterObservableField = new ObservableField<>();

    FilterViewModel(ImageRepository repository, Bitmap path, OnUpdateUIFilter onUpdateUIFilter) {
        mRepository = repository;
        mPath = path;
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        mFilerData = new MutableLiveData();
        mFilterAdapter =  new FilterAdapter(onUpdateUIFilter);
    }

    @Override
    protected void onStart() {
        subcribeData(mPath);
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

    public ObservableField<FilterAdapter> getFilterObservableField() {
        return mFilterObservableField;
    }

    public void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    private LiveData<List<ItemFilter>> getFilterFromLocal(FilterAdapter filterAdapter, Bitmap path) {
        mFilterObservableField.set(filterAdapter);
        Disposable disposable = mRepository.getItemFiltersFromLocal(path)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe( itemFilters ->  {
                    mFilerData.setValue(itemFilters);
                }, throwable -> {
                    mFilerData.setValue(null);
                });
        mCompositeDisposable.add(disposable);
        return mFilerData;
    }

    private void subcribeData(Bitmap path) {
        getFilterFromLocal(mFilterAdapter, path).observe(this, itemFilters -> {
            mFilterAdapter.setFilters(itemFilters);
            mFilterAdapter.notifyDataSetChanged();
        });
    }
}
