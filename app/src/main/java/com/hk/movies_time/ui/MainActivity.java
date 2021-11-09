package com.hk.movies_time.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.hk.movies_time.R;
import com.hk.movies_time.adapter.MovieAdapter;
import com.hk.movies_time.adapter.SearchCallBackListener;
import com.hk.movies_time.databinding.ActivityMainBinding;
import com.hk.movies_time.models.MovieResult;
import com.hk.movies_time.models.TopRatedMovies;
import com.hk.movies_time.utils.Common;
import com.hk.movies_time.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SearchCallBackListener {
    private ActivityMainBinding binding;
    private List<MovieResult> movieList;
    private MovieAdapter movieAdapter;
    MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(MainActivity.this, this);
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModel.init();
        binding.movieRV.setLayoutManager(new LinearLayoutManager(this));
        initiateData();

        binding.goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.clearIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchInput.setText("");
                binding.notFoundView.setVisibility(View.GONE);
            }
        });
    }

    private void initiateData() {
        binding.loginLabel.setText("Hello, " + Common.currentUser.getName());

        if (isConnectedToInternet()) {
            populateMovieData();
        } else {
            setOfflineRules();
        }
    }

    private void populateMovieData() {
        binding.progressLoad.setVisibility(View.VISIBLE);
        viewModel.populateData();
        viewModel.getMoviesLiveData().observe(this, new Observer<TopRatedMovies>() {
            @Override
            public void onChanged(TopRatedMovies topRatedMovies) {
                if (topRatedMovies != null) {
                    movieList = topRatedMovies.getResults();
                    movieAdapter.setMovieList(movieList);
                    binding.searchInput.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() > 0) {
                                binding.clearIV.setVisibility(View.VISIBLE);
                            } else {
                                binding.clearIV.setVisibility(View.GONE);
                            }
                            String text = binding.searchInput.getText().toString().toLowerCase(Locale.getDefault());
                            movieAdapter.filter(text);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    binding.movieRV.setAdapter(movieAdapter);
                    binding.progressLoad.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setOfflineRules() {
        Toast.makeText(MainActivity.this, "You are offline!", Toast.LENGTH_SHORT).show();
    }


    public boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
            if (networkInfo != null) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onEmptySearchList(int itemSize) {
        if (itemSize >= 1) {
            binding.notFoundView.setVisibility(View.GONE);
        } else {
            binding.notFoundView.setVisibility(View.VISIBLE);
        }
    }
}