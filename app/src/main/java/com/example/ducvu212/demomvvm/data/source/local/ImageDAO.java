package com.example.ducvu212.demomvvm.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import io.reactivex.Flowable;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/06.
 */
@Dao
public interface ImageDAO {

    @Query("SELECT * FROM images")
    Flowable<List<ImageRandom>> getAllImages();

    @Query("SELECT * FROM images WHERE image_id = :imageId")
    ImageRandom getUserByUserId(String imageId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertImage(ImageRandom imageRandom);

    @Delete
    void deleteImage(ImageRandom imageRandom);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long updateImage(ImageRandom imageRandom);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateDownload(ImageRandom imageRandom);
}
