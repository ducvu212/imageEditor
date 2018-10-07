package com.example.ducvu212.demomvvm.data.source.local;

import com.example.ducvu212.demomvvm.data.source.ImageDataSource;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class ImageLocalDataSource implements ImageDataSource.ImageLocalDataSource {
    private static ImageLocalDataSource sInstance;
    //    private ImageDAO mImageDAO;

    //    public ImageLocalDataSource(ImageDAO imageDAO) {
    //        mImageDAO = imageDAO;
    //    }

    public static synchronized ImageLocalDataSource getsInstance() {
        if (sInstance == null) {
            synchronized (ImageLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new ImageLocalDataSource();
                }
            }
        }
        return sInstance;
    }

    //    @Override
    //    public Flowable<List<ItemViewPager>> getAllImages() {
    //        return mImageDAO.getAllImages();
    //    }

    //    @Override
    //    public void insertImage(ItemViewPager... itemViewPager) {
    //        mImageDAO.insertImage(itemViewPager);
    //    }
    //
    //    @Override
    //    public void deleteImage(ItemViewPager itemViewPager) {
    //        mImageDAO.deleteImage(itemViewPager);
    //    }
}
