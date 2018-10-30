package com.example.ducvu212.demomvvm.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.example.ducvu212.demomvvm.data.model.Album;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.utils.common.MethodUtils;
import java.util.ArrayList;
import java.util.List;

class GetCollectionsFromLocal {

    private static final String SELECTION = "_data IS NOT NULL) GROUP BY (bucket_display_name";
    private static final String BUCKET_DISPLAY_NAME = "bucket_display_name = \"";
    private static final String PHOTOS = "photos";
    private Context mContext;

    GetCollectionsFromLocal(Context context) {
        mContext = context;
    }

    List<Album> getAllCollectionLocal() {
        List<Album> albums = new ArrayList<>();
        Uri uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_MODIFIED
        };
        Cursor cursorExternal =
                mContext.getContentResolver().query(uriExternal, projection, SELECTION, null, null);
        Cursor cursorInternal =
                mContext.getContentResolver().query(uriInternal, projection, SELECTION, null, null);
        Cursor cursor = new MergeCursor(new Cursor[] { cursorExternal, cursorInternal });

        while (cursor.moveToNext()) {

            String path =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            int countPhoto = getCount(mContext.getApplicationContext(), title);
            albums.add(new Album(path, countPhoto + PHOTOS, title));
        }
        return albums;
    }

    Album getAlbumFromLocal(String albumName) {
        List<Image> images = new ArrayList<>();
        Uri uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_MODIFIED
        };

        Cursor cursorExternal = mContext.getContentResolver()
                .query(uriExternal, projection, BUCKET_DISPLAY_NAME + albumName + "\"", null, null);
        Cursor cursorInternal = mContext.getContentResolver()
                .query(uriInternal, projection, BUCKET_DISPLAY_NAME + albumName + "\"", null, null);
        Cursor cursor = new MergeCursor(new Cursor[] { cursorExternal, cursorInternal });
        while (cursor.moveToNext()) {
            String path =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            String createAt = cursor.getString(
                    cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
            images.add(new Image(path, createAt));
        }
        cursor.close();
        return new Album(albumName, MethodUtils.reverse(images));
    }

    public static int getCount(Context c, String album_name) {
        Uri uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_MODIFIED
        };
        Cursor cursorExternal = c.getContentResolver()
                .query(uriExternal, projection, BUCKET_DISPLAY_NAME + album_name + "\"", null,
                        null);
        Cursor cursorInternal = c.getContentResolver()
                .query(uriInternal, projection, BUCKET_DISPLAY_NAME + album_name + "\"", null,
                        null);
        Cursor cursor = new MergeCursor(new Cursor[] { cursorExternal, cursorInternal });

        return cursor.getCount();
    }
}
