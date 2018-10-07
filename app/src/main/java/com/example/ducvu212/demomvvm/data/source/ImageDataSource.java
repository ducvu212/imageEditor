package com.example.ducvu212.demomvvm.data.source;

import android.app.DownloadManager;
import com.example.ducvu212.demomvvm.data.model.Collection;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.screen.details.ImageDetailsViewListener;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/03.
 */
public interface ImageDataSource {

    /**
     * Local
     */

    interface ImageLocalDataSource {
        //        Flowable<List<ItemViewPager>> getAllImages();
        //
        //        void insertImage(ItemViewPager... itemViewPager);
        //
        //        void deleteImage(ItemViewPager itemViewPager);
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
    }
}
