package com.example.aerveaplay.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aerveaplay.R;
import com.example.aerveaplay.models.MovieModel;

import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovieModels;
    private IOnMovieListener onMovieListener;


    public MovieRecyclerAdapter(IOnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movie_list_item,
                        parent,
                        false);

        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((MovieViewHolder) holder).title.setText(mMovieModels.get(position).getTitle());
        ((MovieViewHolder) holder).release_date.setText(mMovieModels.get(position).getRelease_date());
        ((MovieViewHolder) holder).duration.setText(mMovieModels.get(position).getRuntime());

        // vote average is over 10, and our rating bar is 5 start So we dividing by 2;
        ((MovieViewHolder) holder).ratingBar.setRating((mMovieModels.get(position).getVote_average())/2);

        // get image Movie using Glide;
        Glide.with(holder.itemView.getContext())
                .load(mMovieModels.get(position).getPoster_path())
                .into((((MovieViewHolder)holder).imageMovie));

    }

    @Override
    public int getItemCount() {
        if (mMovieModels != null){
            return mMovieModels.size();
        }
        return 0;
    }

    public void setmMovieModels(List<MovieModel> mMovieModels) {
        this.mMovieModels = mMovieModels;
        notifyDataSetChanged();
    }
}
