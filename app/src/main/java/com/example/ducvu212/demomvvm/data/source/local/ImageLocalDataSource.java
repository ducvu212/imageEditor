package com.example.ducvu212.demomvvm.data.source.local;

import android.content.Context;
import android.graphics.Bitmap;
import com.example.ducvu212.demomvvm.data.model.Album;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.data.model.ItemFilter;
import com.example.ducvu212.demomvvm.data.model.RecentSearch;
import com.example.ducvu212.demomvvm.data.source.ImageDataSource;
import com.example.ducvu212.demomvvm.data.source.realm.RealmRecentSearch;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class ImageLocalDataSource implements ImageDataSource.ImageLocalDataSource {
    private static ImageLocalDataSource sInstance;
    private Context mContext;
    private ImageDAO mImageDAO;

    private ImageLocalDataSource(Context context, ImageDAO imageDAO) {
        mContext = context;
        mImageDAO = imageDAO;
    }

    public static synchronized ImageLocalDataSource getsInstance(ImageDAO imageDAO,
            Context context) {
        if (sInstance == null) {
            synchronized (ImageLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new ImageLocalDataSource(context, imageDAO);
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

    @Override
    public Maybe<List<String>> getTrends() {
        return GetKeyTrendCollection.getKeyTrend();
    }

    @Override
    public Maybe<List<RecentSearch>> getRecentSearch() {
        List<RecentSearch> recentSearches = RealmRecentSearch.getRecentSearchList();
        return Maybe.create(new MaybeOnSubscribe<List<RecentSearch>>() {
            @Override
            public void subscribe(MaybeEmitter<List<RecentSearch>> emitter) throws Exception {
                emitter.onSuccess(recentSearches);
                emitter.onComplete();
            }
        });
    }

    @Override
    public void putRecentSearchToRealm(RecentSearch recentSearch) {
        RealmRecentSearch.addRecentSearch(recentSearch);
    }

    @Override
    public void deleteRecentSearch(RecentSearch recentSearch) {
        RealmRecentSearch.deleteRecentSearch(recentSearch);
    }

    @Override
    public Maybe<List<Album>> getCollectionLocals() {
        return Maybe.create(new MaybeOnSubscribe<List<Album>>() {
            List<Album> albums = new GetCollectionsFromLocal(mContext).getAllCollectionLocal();

            @Override
            public void subscribe(MaybeEmitter<List<Album>> emitter) throws Exception {
                emitter.onSuccess(albums);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Maybe<Album> getAlbumFromLocal(String albumName) {
        return Maybe.create(new MaybeOnSubscribe<Album>() {
            Album album = new GetCollectionsFromLocal(mContext).getAlbumFromLocal(albumName);

            @Override
            public void subscribe(MaybeEmitter<Album> emitter) throws Exception {
                emitter.onSuccess(album);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Maybe<List<ItemFilter>> getAllItemFilter(Bitmap pathImage) {
        return Maybe.create(new MaybeOnSubscribe<List<ItemFilter>>() {
            List<ItemFilter> filters = GetItemFilter.getAllItemFilter(mContext);

            @Override
            public void subscribe(MaybeEmitter<List<ItemFilter>> emitter) throws Exception {
                emitter.onSuccess(filters);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Bitmap getBitmapGallary(String path) {
        return GetItemFilter.getBitmapFromGallary(path);
    }
}
