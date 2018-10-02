package com.example.ducvu212.demomvvm;

import android.app.Application;
import com.example.ducvu212.demomvvm.data.source.remote.config.service.NameServiceClient;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NameServiceClient.initialize(this);
//        UserDbHelper.initializeInstance(this);
    }
}
