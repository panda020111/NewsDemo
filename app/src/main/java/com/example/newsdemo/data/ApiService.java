package com.example.newsdemo.data;

import com.example.newsdemo.data.entity.NewsGson;
import com.example.newsdemo.data.entity.PictureGson;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yunchang on 2018/4/8.
 */

public interface ApiService {

    @GET("{type}/")
    Observable <NewsGson> getNewsData(@Path("type") String type, @Query("key") String key, @Query("num") String num, @Query("page") int page);


    @GET("meinv/")
    Observable <PictureGson> getPictureData(@Query("key")String key, @Query("num") String num, @Query("page") int page);

}
