package com.hk.movies_time.network;

import com.hk.movies_time.models.TopRatedMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("movie/top_rated")
    Call<TopRatedMovies> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language, //"en-US"
            @Query("page") int pageIndex
    );
}
