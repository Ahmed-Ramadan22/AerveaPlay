package com.example.aerveaplay.utils;

import com.example.aerveaplay.responses.MovieSearchResponse;
import com.example.aerveaplay.models.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieApi {

    //Search for Movies
    //   https://api.themoviedb.org/3/search/movie?query=Jack+Reacher&api_key=e2dd873b89d9427e4282098bbb17082c'
    @GET(value = "3/search/movie")
    Call<MovieSearchResponse> searchMovie (
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );

    // Search Link by id:--url
    //  https://api.themoviedb.org/3/movie/343611?api_key=e2dd873b89d9427e4282098bbb17082c

    @GET("3/movie/{id}?")
    Call<MovieModel> getMovie(
            @Path("id") int movie_id,
            @Query("api_key") String api_key
    );

}
