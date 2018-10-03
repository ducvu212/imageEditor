package com.example.ducvu212.demomvvm.data.source.remote;

import com.example.ducvu212.demomvvm.BuildConfig;
import com.example.ducvu212.demomvvm.data.api.ApiClient;
import com.example.ducvu212.demomvvm.data.api.ApiInterface;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.source.ImageDataSource;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by CuD HniM on 18/09/25.
 */
public class ImageRemoteDataSource implements ImageDataSource.ImageRemoteDataSource {

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
        return ApiClient.getInstance()
                .create(ApiInterface.class)
                .getRandomsImage(BuildConfig.API_KEY);
    }
}
