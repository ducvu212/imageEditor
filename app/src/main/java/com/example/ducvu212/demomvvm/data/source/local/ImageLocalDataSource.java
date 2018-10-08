package com.example.ducvu212.demomvvm.data.source.local;

import com.example.ducvu212.demomvvm.data.model.RecentSearch;
import com.example.ducvu212.demomvvm.data.source.ImageDataSource;
import com.example.ducvu212.demomvvm.data.source.realm.RealmRecentSearch;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class ImageLocalDataSource implements ImageDataSource.ImageLocalDataSource {
    private static ImageLocalDataSource sInstance;
    //    private ImageDAO mImageDAO;

    //    public ImageLocalDataSource(ImageDAO imageDAO) {
    //        mImageDAO = imageDAO;
    //    }

    public static synchronized ImageLocalDataSource getsInstance() {
        if (sInstance == null) {
            synchronized (ImageLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new ImageLocalDataSource();
                }
            }
        }
        return sInstance;
    }

    @Override
    public Maybe<List<String>> getTrends() {
        return GetKeyTrendCollection.getKeyTrend();
    }

    @Override
    public Maybe<List<RecentSearch>> getRecentSearch() {
        List<RecentSearch> recentSearches = RealmRecentSearch.getRecentSearchList();
        return Maybe.create(new MaybeOnSubscribe<List<RecentSearch>>() {
            @Override
            public void subscribe(MaybeEmitter<List<RecentSearch>> emitter) throws Exception {
                emitter.onSuccess(recentSearches);
                emitter.onComplete();
            }
        });
    }

    @Override
    public void putRecentSearchToRealm(RecentSearch recentSearch) {
        RealmRecentSearch.addRecentSearch(recentSearch);
    }

    @Override
    public void deleteRecentSearch(RecentSearch recentSearch) {
        RealmRecentSearch.deleteRecentSearch(recentSearch);
    }

    //    @Override
    //    public Flowable<List<ItemViewPager>> getAllImages() {
    //        return mImageDAO.getAllImages();
    //    }

    //    @Override
    //    public void insertImage(ItemViewPager... itemViewPager) {
    //        mImageDAO.insertImage(itemViewPager);
    //    }
    //
    //    @Override
    //    public void deleteImage(ItemViewPager itemViewPager) {
    //        mImageDAO.deleteImage(itemViewPager);
    //    }
}
