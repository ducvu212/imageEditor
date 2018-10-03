package com.example.ducvu212.demomvvm.data.source.remote.config.service;

import android.app.Application;
import android.support.annotation.NonNull;
import com.example.ducvu212.demomvvm.utils.Constant;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class NameServiceClient extends ServiceClient {

    private static NameApi mNameApiInstance;

    public static void initialize(@NonNull Application application) {
        mNameApiInstance = createService(application, Constant.END_POINT_URL, NameApi.class);
    }

    public static NameApi getInstance() {
        if (mNameApiInstance == null) {
            throw new IllegalStateException(NameServiceClient.class.getSimpleName()
                    + " is not initialized, call initialize(..) method first.");        }
        return mNameApiInstance;
    }
}
