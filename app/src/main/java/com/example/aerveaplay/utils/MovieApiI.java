package com.example.aerveaplay.utils;

import com.example.aerveaplay.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiI {

    //Search for Movies
    //   https://api.themoviedb.org/3/search/movie?query=Jack+Reacher&api_key=b999f02e2612ffea06b3b736f4f5c1bf'
    @GET(value = "3/movie/top_rated")
    Call<MovieSearchResponse> searchMovie (
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );


}
