package com.example.ducvu212.demomvvm.data.source.local;

import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.data.source.ImageDataSource;
import io.reactivex.Flowable;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class ImageLocalDataSource implements ImageDataSource.ImageLocalDataSource {
    private static ImageLocalDataSource sInstance;
    private ImageDAO mImageDAO;

    private ImageLocalDataSource(ImageDAO imageDAO) {
        mImageDAO = imageDAO;
    }

    public static synchronized ImageLocalDataSource getsInstance(ImageDAO imageDAO) {
        if (sInstance == null) {
            synchronized (ImageLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new ImageLocalDataSource(imageDAO);
                }
            }
        }
        return sInstance;
    }

    @Override
    public Flowable<List<ImageRandom>> getAllImages() {
        return mImageDAO.getAllImages();
    }

    @Override
    public ImageRandom getImageById(String id) {
        return mImageDAO.getUserByUserId(id);
    }

    @Override
    public void insertImage(ImageRandom imageRandom) {
        mImageDAO.insertImage(imageRandom);
    }

    @Override
    public void deleteImage(ImageRandom imageRandom) {
        mImageDAO.deleteImage(imageRandom);
    }

    @Override
    public void updateImage(ImageRandom imageRandom) {
        mImageDAO.updateImage(imageRandom);
    }

    @Override
    public void updateDownload(ImageRandom imageRandom) {
        mImageDAO.updateDownload(imageRandom);
    }
}
