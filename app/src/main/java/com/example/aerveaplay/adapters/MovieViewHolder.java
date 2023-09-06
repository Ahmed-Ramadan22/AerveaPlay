package com.example.aerveaplay.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aerveaplay.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    // initialize widgets
    TextView title, release_date, duration;
    ImageView imageMovie;
    RatingBar ratingBar;

    // click listener
    IOnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View view, IOnMovieListener onMovieListener) {
        super(view);

        this.onMovieListener = onMovieListener;

        title = view.findViewById(R.id.movie_title_item);
        release_date = view.findViewById(R.id.movie_category_item);
        duration = view.findViewById(R.id.movie_duration_item);

        imageMovie = view.findViewById(R.id.image_movie_item);
        ratingBar = view.findViewById(R.id.movie_rating_item);

        view.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }

}
