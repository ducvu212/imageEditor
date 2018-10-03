package com.example.ducvu212.demomvvm.data.api;

import com.example.ducvu212.demomvvm.data.model.Image;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("photos?client_id")
    Single<List<Image>> getRandomsImage(@Query("client_id") String apiKey);
}
