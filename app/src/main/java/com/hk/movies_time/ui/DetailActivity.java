package com.hk.movies_time.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hk.movies_time.R;
import com.hk.movies_time.databinding.ActivityDetailBinding;
import com.hk.movies_time.models.MovieResult;
import com.hk.movies_time.models.TopRatedMovies;
import com.hk.movies_time.viewmodel.MovieViewModel;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private int movieId;
    MovieViewModel viewModel;
    private MovieResult result = null;
    private ActivityDetailBinding binding;
    String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w400";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModel.init();
        //get intent
        if (getIntent().getIntExtra("id", -1) != -1) {
            movieId = getIntent().getIntExtra("id", -1);
            Log.d("DetailActivity", "onCreate: " + movieId);
        }
        initiateData();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initiateData() {
        viewModel.populateData();
        viewModel.getMoviesLiveData().observe(this, new Observer<TopRatedMovies>() {
            @Override
            public void onChanged(TopRatedMovies topRatedMovies) {
                for (MovieResult movieResult : topRatedMovies.getResults()) {
                    if (movieResult.getId().equals(movieId)) {
                        result = movieResult;
                        populateData();
                    }
                }
            }
        });

    }

    public void populateData() {
        if (result != null) {
            //set details
            Picasso.get().load(POSTER_BASE_URL + result.getBackdropPath()).placeholder(R.drawable.add_gallery).into(binding.movieIV);

            binding.nameTV.setText(result.getTitle());
            binding.adultTV.setText(result.getAdult() ? "Yes" : "No");
            binding.ratingTV.setText(String.valueOf(result.getVoteAverage()));
            binding.storyLineTV.setText(result.getOverview());
            binding.releaseDate.setText(result.getReleaseDate());

            binding.progressLoad.setVisibility(View.GONE);
        }
    }
}