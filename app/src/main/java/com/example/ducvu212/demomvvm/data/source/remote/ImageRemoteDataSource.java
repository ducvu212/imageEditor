package com.example.ducvu212.demomvvm.data.source.remote;

import com.example.ducvu212.demomvvm.BuildConfig;
import com.example.ducvu212.demomvvm.data.api.ApiClient;
import com.example.ducvu212.demomvvm.data.api.ApiInterface;
import com.example.ducvu212.demomvvm.data.model.Collection;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.source.ImageDataSource;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by CuD HniM on 18/09/25.
 */
public class ImageRemoteDataSource implements ImageDataSource.ImageRemoteDataSource {

    private static ImageRemoteDataSource sInstance;
    private static final int NUMBER_RANDOM = 10;

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

    private ApiInterface createApiInterface() {
        return ApiClient.getInstance().create(ApiInterface.class);
    }
}
