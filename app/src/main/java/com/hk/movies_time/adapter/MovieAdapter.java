package com.hk.movies_time.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.hk.movies_time.R;
import com.hk.movies_time.models.MovieResult;
import com.hk.movies_time.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w400";
    private List<MovieResult> movieList;
    private List<MovieResult> searchableMovieList;
    private SearchCallBackListener searchCallBackListener;
    private Context context;

    public MovieAdapter(Context context, SearchCallBackListener searchCallBackListener) {
        this.context = context;
        this.searchCallBackListener = searchCallBackListener;
    }

    public void setMovieList(List<MovieResult> movieList) {
        this.movieList = movieList;
        searchableMovieList = new ArrayList<>();
        this.searchableMovieList.addAll(movieList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MovieResult result = movieList.get(position);
        Picasso.get().load(POSTER_BASE_URL + result.getBackdropPath()).placeholder(R.drawable.add_gallery).into(holder.poster);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.poster.setClipToOutline(true);
        }
        holder.name.setText(result.getTitle());
        holder.releaseDate.setText(result.getReleaseDate());
        holder.adultCheck.setText(result.getAdult() ? "Yes" : "No");
        holder.rating.setText(String.valueOf(result.getVoteAverage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide soft keyboard from search view
                View view = ((Activity) context).getCurrentFocus();
                InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                try {
                    im.hideSoftInputFromWindow(view.getWindowToken(), 0); // make keyboard hide
                } catch (NullPointerException e) {
                    Log.d("Madapter", "setAppBaeState: NullPointerException: " + e);
                }


                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", result.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView poster;
        AppCompatTextView name, rating, adultCheck, releaseDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.movieIV);
            name = itemView.findViewById(R.id.nameTV);
            rating = itemView.findViewById(R.id.ratingTV);
            adultCheck = itemView.findViewById(R.id.adultTV);
            releaseDate = itemView.findViewById(R.id.releaseDate);
        }
    }

    // filter name in Search Bar
    public void filter(String characterText) {
        characterText = characterText.toLowerCase(Locale.getDefault());
        movieList.clear();
        if (characterText.length() == 0) { //initially empty search word populated full list
            movieList.addAll(searchableMovieList);
        } else {
            movieList.clear();
            for (MovieResult movie : searchableMovieList) {
                if (movie.getTitle().toLowerCase(Locale.getDefault()).contains(characterText)) {
                    movieList.add(movie);
                }
            }

            //if empty list on search
            searchCallBackListener.onEmptySearchList(movieList.size());


        }
        notifyDataSetChanged();
    }
}
