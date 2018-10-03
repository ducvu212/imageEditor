package com.example.ducvu212.demomvvm.data.source;

import com.example.ducvu212.demomvvm.data.model.Image;
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

    }

    /**
     * Remote
     */
    interface ImageRemoteDataSource {
        Single<List<Image>> getRandomImages();
    }
}
