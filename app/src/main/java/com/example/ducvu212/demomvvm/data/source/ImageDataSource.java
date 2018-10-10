package com.example.ducvu212.demomvvm.data.source;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import com.example.ducvu212.demomvvm.data.model.Collection;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.screen.details.ImageDetailsViewListener;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by CuD HniM on 18/10/03.
 */
public interface ImageDataSource {

    /**
     * Local
     */

    interface ImageLocalDataSource {
        Flowable<List<ImageRandom>> getAllImages();

        ImageRandom getImageById(String id);

        void insertImage(ImageRandom imageRandom);

        void deleteImage(ImageRandom imageRandom);

        void updateImage(ImageRandom imageRandom);

        void updateDownload(ImageRandom imageRandom);
    }

    /**
     * Remote
     */
    interface ImageRemoteDataSource {
        Single<List<Image>> getRandomImages();

        Single<List<Collection>> getCollections(int page);

        Single<List<Image>> getNewImages(int page, String apiKey);

        Single<List<Image>> getCollectionImages(int id, int page, String apiKey);

        void downloadImage(DownloadManager manager, String url, String name,
                ImageDetailsViewListener listener);

        Bitmap getBitmapFromUrl(String url) throws ExecutionException, InterruptedException;
    }
}
