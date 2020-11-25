package com.android.alfaazpractical.api;

import com.android.alfaazpractical.model.Main;
import com.android.alfaazpractical.model.Movie;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<JsonObject> getTopRaed(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<JsonObject> getMovieDetail(@Path("movie_id") Integer id, @Query("api_key") String apiKey);
}
