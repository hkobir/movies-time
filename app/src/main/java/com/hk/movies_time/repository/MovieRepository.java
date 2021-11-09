package com.hk.movies_time.repository;

import androidx.lifecycle.MutableLiveData;

import com.hk.movies_time.models.TopRatedMovies;
import com.hk.movies_time.network.MovieApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private String API_KEY = "4889e0a2629c8329d9cbe5b330710a0f";
    private MovieApi movieApi;
    private MutableLiveData<TopRatedMovies> moviesLiveData;

    public MovieRepository() {
        moviesLiveData = new MutableLiveData<>();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        movieApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi.class);
    }

    public void populateMoviesData() {
        movieApi.getTopRatedMovies(API_KEY, "en-US", 1).enqueue(new Callback<TopRatedMovies>() {
            @Override
            public void onResponse(Call<TopRatedMovies> call, Response<TopRatedMovies> response) {
                if (response.body() != null) {
                    moviesLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TopRatedMovies> call, Throwable t) {
                moviesLiveData.postValue(null);
            }
        });
    }

    public MutableLiveData<TopRatedMovies> getMoviesLiveData() {
        return moviesLiveData;
    }
}
