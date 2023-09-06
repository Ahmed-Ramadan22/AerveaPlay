package com.example.aerveaplay.home.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aerveaplay.R;
import com.example.aerveaplay.adapters.IOnMovieListener;
import com.example.aerveaplay.adapters.MovieRecyclerAdapter;
import com.example.aerveaplay.request.Servicey;
import com.example.aerveaplay.responses.MovieSearchResponse;
import com.example.aerveaplay.utils.Credentials;
import com.example.aerveaplay.utils.IMovieApi;
import com.example.aerveaplay.models.MovieModel;
import com.example.aerveaplay.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements IOnMovieListener {

    private RecyclerView popularMovieRecycler;
    private MovieRecyclerAdapter movieRecyclerAdapter;
    private static String TAG = "Tag";

    // viewModel Implement
    private MovieListViewModel movieListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        popularMovieRecycler = view.findViewById(R.id.popular_movies_recycler);
        // viewModel Provider
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //Search movie EditText
        SetupSearchMovie();

        //Calling a Recycler Movies;
        ConfigRecyclerMovies();
        // calling the observer
        ObserveAnyChange();
        searchMovieApi("fast", 1);


    }

    private void SetupSearchMovie() {


    }

    //initialize a Recycler & Adding a data to it;
    private void ConfigRecyclerMovies() {
        movieRecyclerAdapter = new MovieRecyclerAdapter(this);
        popularMovieRecycler.setAdapter(movieRecyclerAdapter);
        popularMovieRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onMovieClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }

    // Observing any Data Change.
    private void ObserveAnyChange() {
        movieListViewModel.getMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                if (movieModels != null) {
                    for (MovieModel movieModel : movieModels) {
                        // get data in Log
                        Log.v(TAG, " onChanged:  " + movieModel.getTitle());
                        movieRecyclerAdapter.setmMovieModels(movieModels);
                    }
                }

            }
        });
    }


    // 4- Calling Method in Home Fragment
    public void searchMovieApi(String query, int pageNumber) {
        movieListViewModel.searchMovieApi(query, pageNumber);
    }

    private void GetRetrofitResponse() {

        IMovieApi IMovieApi = Servicey.getMovieApi();

        Call<MovieSearchResponse> responseCall = IMovieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Action",
                        "1");

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                if (response.code() == 200) {
                    Log.v(TAG, "The response " + response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie : movies) {
                        Log.v(TAG, "The Release date " + movie.getTitle());
                    }

                } else {

                    try {
                        Log.v(TAG, "Error: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    //This Function for Get Movie by id
    private void GetRetrofitResponseAccordingToID() {

        IMovieApi IMovieApi = Servicey.getMovieApi();
        Call<MovieModel> responseCall = IMovieApi
                .getMovie(
                        343611,
                        Credentials.API_KEY);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    MovieModel model = response.body();
                    Log.v(TAG, "The Response: " + model.getTitle());
                } else {

                    try {
                        Log.v(TAG, "Error " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}



