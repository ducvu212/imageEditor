package com.example.ducvu212.demomvvm;

import android.app.Application;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
