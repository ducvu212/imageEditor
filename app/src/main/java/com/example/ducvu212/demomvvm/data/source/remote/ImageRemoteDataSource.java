package com.example.ducvu212.demomvvm.data.source.remote;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import com.example.ducvu212.demomvvm.BuildConfig;
import com.example.ducvu212.demomvvm.data.api.ApiClient;
import com.example.ducvu212.demomvvm.data.api.ApiInterface;
import com.example.ducvu212.demomvvm.data.model.Collection;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.model.SearchRespond;
import com.example.ducvu212.demomvvm.data.source.ImageDataSource;
import com.example.ducvu212.demomvvm.screen.details.ImageDetailsViewListener;
import io.reactivex.Single;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by CuD HniM on 18/09/25.
 */
public class ImageRemoteDataSource implements ImageDataSource.ImageRemoteDataSource {

    private static final int NUMBER_RANDOM = 10;
    private static ImageRemoteDataSource sInstance;

    public static synchronized ImageRemoteDataSource getsInstance() {
        if (sInstance == null) {
            synchronized (ImageRemoteDataSource.class) {
                if (sInstance == null) {
                    sInstance = new ImageRemoteDataSource();
                }
            }
        }
        return sInstance;
    }

    @Override
    public Single<List<Image>> getRandomImages() {
        return createApiInterface().getRandomsImage(BuildConfig.API_KEY, NUMBER_RANDOM);
    }

    @Override
    public Single<List<Collection>> getCollections(int page) {
        return createApiInterface().getCollections(page, BuildConfig.API_KEY);
    }

    @Override
    public Single<List<Image>> getNewImages(int page, String apiKey) {
        return createApiInterface().getNewImage(page, apiKey);
    }

    @Override
    public Single<List<Image>> getCollectionImages(int id, int page, String apiKey) {
        return createApiInterface().getCollectionDetails(id, page, apiKey);
    }

    @Override
    public void downloadImage(DownloadManager manager, String url, String name,
            ImageDetailsViewListener listener) {
        new Download(manager, listener).download(url, name);
    }

    @Override
    public Bitmap getBitmapFromUrl(String url) throws ExecutionException, InterruptedException {
        return new ConvertBitmapAsyncTask().execute(url).get();
    }

    @Override
    public Single<SearchRespond> searchCollection(int page, String query, String apiKey) {
        return createApiInterface().searchCollection(page, query, apiKey);
    }

    private ApiInterface createApiInterface() {
        return ApiClient.getInstance().create(ApiInterface.class);
    }
}
