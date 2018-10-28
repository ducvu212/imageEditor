package com.example.ducvu212.demomvvm.screen.search;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import com.example.ducvu212.demomvvm.BuildConfig;
import com.example.ducvu212.demomvvm.data.model.Collection;
import com.example.ducvu212.demomvvm.data.model.RecentSearch;
import com.example.ducvu212.demomvvm.data.model.SearchRespond;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.screen.base.BaseViewModel;
import com.example.ducvu212.demomvvm.screen.search.adapter.CollectionSearchAdapter;
import com.example.ducvu212.demomvvm.screen.search.adapter.RecentSearchAdapter;
import com.example.ducvu212.demomvvm.screen.search.adapter.TrendAdapter;
import com.example.ducvu212.demomvvm.utils.common.MethodUtils;
import com.example.ducvu212.demomvvm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;

public class SearchViewModel extends BaseViewModel implements LifecycleOwner {
    private Context mContext;
    private LifecycleRegistry mLifecycleRegistry;
    private CollectionSearchAdapter mSearchAdapter;
    private TrendAdapter mTrendAdapter;
    private RecentSearchAdapter mRecentSearchAdapter;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ImageRepository mImageRepository;
    private int mPageCollectionNumber = 1;
    private MutableLiveData<SearchRespond> mCollectionMutableLiveData;
    private MutableLiveData<List<String>> mTrendMutableLiveData;
    private MutableLiveData<List<RecentSearch>> mRecentMutableLiveData;
    private ObservableField<CollectionSearchAdapter> mSearchAdapterObservableField =
            new ObservableField<>();
    private ObservableField<TrendAdapter> mTrendAdapterObservableField = new ObservableField<>();
    private ObservableField<RecentSearchAdapter> mRecentSearchAdapterObservableField =
            new ObservableField<>();

    public SearchViewModel(Context context, ImageRepository imageRepository,
            FragmentManager manager) {
        mContext = context;
        mImageRepository = imageRepository;
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        mCollectionMutableLiveData = new MutableLiveData<>();
        mTrendMutableLiveData = new MutableLiveData<>();
        mRecentMutableLiveData = new MutableLiveData<>();
        mSearchAdapter = new CollectionSearchAdapter(manager);
        mTrendAdapter = new TrendAdapter();
        mRecentSearchAdapter = new RecentSearchAdapter();
    }

    @Override
    protected void onStart() {
        subcribeTrend();
        subcribeRecentSearch();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    public ObservableField<CollectionSearchAdapter> getObservableField() {
        return mSearchAdapterObservableField;
    }

    public ObservableField<TrendAdapter> getTrendAdapterObservableField() {
        return mTrendAdapterObservableField;
    }

    public ObservableField<RecentSearchAdapter> getRecentSearchAdapterObservableField() {
        return mRecentSearchAdapterObservableField;
    }

    void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    private LiveData<SearchRespond> searchCollection(CollectionSearchAdapter searchAdapter,
            int page, String query) {
        mSearchAdapterObservableField.set(searchAdapter);
        Disposable disposable = mImageRepository.searchCollection(page, query, BuildConfig.API_KEY)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<SearchRespond>() {
                    @Override
                    public void accept(SearchRespond searchRespond) throws Exception {
                        mCollectionMutableLiveData.setValue(searchRespond);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mCollectionMutableLiveData.setValue(null);
                    }
                });
        mCompositeDisposable.add(disposable);
        return mCollectionMutableLiveData;
    }

    private LiveData<List<String>> getKeyTrendCollection(TrendAdapter trendAdapte) {
        mTrendAdapterObservableField.set(trendAdapte);
        Disposable disposable = mImageRepository.getKeyTrendCollections()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> trends) throws Exception {
                        mTrendMutableLiveData.setValue(trends);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mTrendMutableLiveData.setValue(null);
                    }
                });
        mCompositeDisposable.add(disposable);
        return mTrendMutableLiveData;
    }

    private LiveData<List<RecentSearch>> getRecentSearch(RecentSearchAdapter recentSearchAdapter) {
        mRecentSearchAdapterObservableField.set(recentSearchAdapter);
        Disposable disposable = mImageRepository.getRecentSearchs()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<RecentSearch>>() {
                    @Override
                    public void accept(List<RecentSearch> recentSearches) throws Exception {
                        mRecentMutableLiveData.setValue(recentSearches);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mRecentMutableLiveData.setValue(null);
                    }
                });
        mCompositeDisposable.add(disposable);
        return mRecentMutableLiveData;
    }

    public void subcribeCollection(String query) {
        searchCollection(mSearchAdapter, mPageCollectionNumber, query).observe(this,
                new Observer<SearchRespond>() {
                    @Override
                    public void onChanged(@Nullable SearchRespond searchRespond) {
                        List<Collection> collections = searchRespond.getCollections();
                        mSearchAdapter.setCollections(collections);
                        mSearchAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void subcribeTrend() {
        getKeyTrendCollection(mTrendAdapter).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                mTrendAdapter.setTrends(strings);
                mTrendAdapter.notifyDataSetChanged();
            }
        });
    }

    public void subcribeRecentSearch() {
        getRecentSearch(mRecentSearchAdapter).observe(this, new Observer<List<RecentSearch>>() {
            @Override
            public void onChanged(@Nullable List<RecentSearch> recentSearches) {
                mRecentSearchAdapter.setRecentSearch(MethodUtils.reverse(recentSearches));
                mRecentSearchAdapter.notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    public void putRecentSearchToRealm(String recent) {
        mImageRepository.addRecentSearchToRealm(new RecentSearch(recent));
    }

    public void deleteRecentSearch(RecentSearch recentSearch) {
        mImageRepository.deleteRecentSearch(recentSearch);
    }
}
