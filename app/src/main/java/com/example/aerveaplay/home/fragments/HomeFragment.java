package com.example.aerveaplay.home.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aerveaplay.R;
import com.example.aerveaplay.request.Servicey;
import com.example.aerveaplay.response.MovieSearchResponse;
import com.example.aerveaplay.utils.Credentials;
import com.example.aerveaplay.utils.MovieApiI;
import com.example.models.MovieModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private Button btn_test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_test = view.findViewById(R.id.test_btn);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetRetrofitResponseAccordingToID();
            }
        });


    }

    private void GetRetrofitResponse() {

        MovieApiI movieApiI = Servicey.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApiI
                .searchMovie(
                        Credentials.API_KEY,
                        "Action",
                        "1" );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                if (response.code() == 200){
                    Log.v("Tag","The response "+ response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie: movies){
                        Log.v("Tag","The Release date "+ movie.getTitle());
                    }

                } else {

                    try {
                        Log.v("Tag", "Error: "+ response.errorBody().string());
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
    private void GetRetrofitResponseAccordingToID(){

        MovieApiI movieApiI = Servicey.getMovieApi();
        Call<MovieModel> responseCall = movieApiI
                .getMovie(
                        346,
                        Credentials.API_KEY);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200){
                    MovieModel model = response.body();
                    Log.v("Tag", "The Response: " +model.getTitle());
                }else {

                    try {
                        Log.v("Tag", "Error " + response.errorBody().string());
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



