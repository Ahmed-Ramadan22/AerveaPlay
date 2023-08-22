package com.example.aerveaplay.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aerveaplay.models.MovieModel;
import com.example.aerveaplay.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;
    private MovieApiClient movieApiClient;


    //Single Ton pattern
    public static MovieRepository getInstance(){
        if (instance == null ) {
            instance = new MovieRepository();
        }
        return  instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }



}
