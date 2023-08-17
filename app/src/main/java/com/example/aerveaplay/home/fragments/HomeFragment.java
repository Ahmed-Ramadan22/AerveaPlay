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
                GetRetrofitResponse();
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


}
