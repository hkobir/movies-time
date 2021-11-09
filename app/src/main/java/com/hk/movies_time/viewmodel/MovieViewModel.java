package com.hk.movies_time.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hk.movies_time.models.TopRatedMovies;
import com.hk.movies_time.repository.MovieRepository;

public class MovieViewModel extends AndroidViewModel {
    MovieRepository movieRepository;
    private MutableLiveData<TopRatedMovies> moviesLiveData;

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        movieRepository = new MovieRepository();
        moviesLiveData = movieRepository.getMoviesLiveData();
    }

    public void populateData() {
        movieRepository.populateMoviesData();
    }

    public MutableLiveData<TopRatedMovies> getMoviesLiveData() {
        return moviesLiveData;
    }
}
