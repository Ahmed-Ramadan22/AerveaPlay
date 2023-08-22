package com.example.aerveaplay.request;

import com.example.aerveaplay.utils.Credentials;
import com.example.aerveaplay.utils.IMovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicey {

    //retrofit builder
    private static Retrofit.Builder retrofitBuilder = new Retrofit
            .Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static IMovieApi movieApi = retrofit.create(IMovieApi.class);

    public static IMovieApi getMovieApi(){
        return movieApi;
    }

}
