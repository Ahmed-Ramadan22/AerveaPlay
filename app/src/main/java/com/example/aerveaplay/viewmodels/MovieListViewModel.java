package com.example.aerveaplay.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aerveaplay.models.MovieModel;
import com.example.aerveaplay.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {


    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }



}
