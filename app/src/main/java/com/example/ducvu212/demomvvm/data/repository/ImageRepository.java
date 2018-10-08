package com.example.ducvu212.demomvvm.data.repository;

import android.app.DownloadManager;
import com.example.ducvu212.demomvvm.data.model.Collection;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.model.RecentSearch;
import com.example.ducvu212.demomvvm.data.model.SearchRespond;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.screen.details.ImageDetailsViewListener;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class ImageRepository {

    private static ImageRepository sInstance;
    private ImageRemoteDataSource mRemoteDataSource;
    private ImageLocalDataSource mLocalDataSource;

    public ImageRepository(ImageRemoteDataSource remoteDataSource,
            ImageLocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static synchronized ImageRepository getsInstance(
            ImageRemoteDataSource imageRemoteDataSource,
            ImageLocalDataSource imageLocalDataSource) {
        if (sInstance == null) {
            synchronized (ImageRemoteDataSource.class) {
                if (sInstance == null) {
                    sInstance = new ImageRepository(imageRemoteDataSource, imageLocalDataSource);
                }
            }
        }
        return sInstance;
    }

    public Single<List<Image>> getRandomImage() {
        return mRemoteDataSource.getRandomImages();
    }

    public Single<List<Collection>> getCollections(int page) {
        return mRemoteDataSource.getCollections(page);
    }

    public Single<List<Image>> getNewImages(int page, String apiKey) {
        return mRemoteDataSource.getNewImages(page, apiKey);
    }

    //    public Flowable<List<ItemViewPager>> getAllImages() {
    //        return mLocalDataSource.getAllImages();
    //    }
    //
    //    public void insertImage(ItemViewPager itemViewPager) {
    //        mLocalDataSource.insertImage(itemViewPager);
    //    }
    //
    //    public void deleteImage(ItemViewPager itemViewPager) {
    //        mLocalDataSource.deleteImage(itemViewPager);
    //    }

    public void dowloadImage(DownloadManager manager, String url, String name,
            ImageDetailsViewListener listener) {
        mRemoteDataSource.downloadImage(manager, url, name, listener);
    }

    public Single<List<Image>> getCollectionsDetails(int id, int page, String apiKey) {
        return mRemoteDataSource.getCollectionImages(id, page, apiKey);
    }

    public Single<SearchRespond> searchCollection(int page, String query, String apiKey) {
        return mRemoteDataSource.searchCollection(page, query, apiKey);
    }

    public Maybe<List<String>> getKeyTrendCollections() {
        return mLocalDataSource.getTrends();
    }

    public Maybe<List<RecentSearch>> getRecentSearchs() {
        return mLocalDataSource.getRecentSearch();
    }

    public void addRecentSearchToRealm(RecentSearch recentSearch) {
        mLocalDataSource.putRecentSearchToRealm(recentSearch);
    }

    public void deleteRecentSearch(RecentSearch recentSearch) {
        mLocalDataSource.deleteRecentSearch(recentSearch);
    }
}
