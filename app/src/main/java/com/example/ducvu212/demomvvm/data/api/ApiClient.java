package com.example.ducvu212.demomvvm.data.api;

import com.example.ducvu212.demomvvm.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ducvu212.demomvvm.utils.Constant.BASE_URL;
import static com.example.ducvu212.demomvvm.utils.Constant.LOGGING_REQUEST;
import static com.example.ducvu212.demomvvm.utils.Constant.LOGGING_RESPONE;
import static com.example.ducvu212.demomvvm.utils.Constant.LOGGING_VERSION;

public class ApiClient {

    private static Retrofit sInstance;

    public static synchronized Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.addInterceptor(new LoggingInterceptor.Builder().loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request(LOGGING_REQUEST)
                .response(LOGGING_RESPONE)
                .addHeader(LOGGING_VERSION, BuildConfig.VERSION_NAME)
                .build());
        okBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okBuilder.interceptors().add(logging);
        if (sInstance == null) {
            synchronized (ApiClient.class) {
                if (sInstance == null) {
                    sInstance = new Retrofit.Builder().baseUrl(BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(okBuilder.build())
                            .build();
                }
            }
        }
        return sInstance;
    }
}
