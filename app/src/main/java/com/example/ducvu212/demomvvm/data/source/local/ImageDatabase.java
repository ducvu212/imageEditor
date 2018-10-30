package com.example.ducvu212.demomvvm.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;

import static com.example.ducvu212.demomvvm.data.source.local.ImageDatabase.DATABASE_VERSION;

/**
 * Created by CuD HniM on 18/10/06.
 */
@Database(entities = { ImageRandom.class }, version = DATABASE_VERSION)
public abstract class ImageDatabase extends RoomDatabase {
    static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "images-db";

    private static ImageDatabase sImageDatabase;

    public static ImageDatabase getInstance(Context context) {
        if (sImageDatabase == null) {
            sImageDatabase = Room.databaseBuilder(context, ImageDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return sImageDatabase;
    }

    public abstract ImageDAO mImageDAO();
}
