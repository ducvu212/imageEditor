package com.example.ducvu212.demomvvm.data.repository;

import com.example.ducvu212.demomvvm.data.model.Collection;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
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
}
