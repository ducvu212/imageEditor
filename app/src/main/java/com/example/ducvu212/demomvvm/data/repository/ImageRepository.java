package com.example.ducvu212.demomvvm.data.repository;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import com.example.ducvu212.demomvvm.data.model.Album;
import com.example.ducvu212.demomvvm.data.model.Collection;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.data.model.ItemFilter;
import com.example.ducvu212.demomvvm.data.model.RecentSearch;
import com.example.ducvu212.demomvvm.data.model.SearchRespond;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.screen.details.ImageDetailsViewListener;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public Flowable<List<ImageRandom>> getAllImages() {
        return mLocalDataSource.getAllImages();
    }

    public void insertImage(ImageRandom itemViewPager) {
        mLocalDataSource.insertImage(itemViewPager);
    }

    public void deleteImage(ImageRandom itemViewPager) {
        mLocalDataSource.deleteImage(itemViewPager);
    }

    public void dowloadImage(DownloadManager manager, String url, String name,
            ImageDetailsViewListener listener) {
        mRemoteDataSource.downloadImage(manager, url, name, listener);
    }

    public Single<List<Image>> getCollectionsDetails(int id, int page, String apiKey) {
        return mRemoteDataSource.getCollectionImages(id, page, apiKey);
    }

    public ImageRandom getImageById(String id) {
        return mLocalDataSource.getImageById(id);
    }

    public void updateImage(ImageRandom imageRandom) {
        mLocalDataSource.updateImage(imageRandom);
    }

    public void updateDownload(ImageRandom imageRandom) {
        mLocalDataSource.updateDownload(imageRandom);
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

    public Maybe<List<Album>> getCollectionsFromLoacl() {
        return mLocalDataSource.getCollectionLocals();
    }

    public Maybe<Album> getAlbumFromLocal(String albumName) {
        return mLocalDataSource.getAlbumFromLocal(albumName);
    }

    public Maybe<List<ItemFilter>> getItemFiltersFromLocal (Bitmap path) {
        return mLocalDataSource.getAllItemFilter(path);
    }

    public Bitmap getBitmap(String url) throws ExecutionException, InterruptedException {
        return mRemoteDataSource.getBitmapFromUrl(url);
    }

    public Bitmap getBitmapGallary(String path) {
        return mLocalDataSource.getBitmapGallary(path);
    }
}
