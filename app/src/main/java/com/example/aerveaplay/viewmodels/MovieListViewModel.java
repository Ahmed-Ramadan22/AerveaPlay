package com.example.aerveaplay.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aerveaplay.models.MovieModel;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MutableLiveData<List<MovieModel>> mMoviesLiveData = new MutableLiveData<>();

    public MovieListViewModel() {}

    public LiveData<List<MovieModel>> getMovies(){
        return mMoviesLiveData;
    }



}
