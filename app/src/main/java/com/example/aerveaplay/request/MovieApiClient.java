package com.example.aerveaplay.request;

import android.credentials.Credential;
import android.nfc.Tag;
import android.renderscript.ScriptGroup;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aerveaplay.AppExecutors;
import com.example.aerveaplay.models.MovieModel;
import com.example.aerveaplay.responses.MovieSearchResponse;
import com.example.aerveaplay.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private MutableLiveData<List<MovieModel>> mMoviesLiveData;
    private static MovieApiClient instance;
    private static String TAG = "Tag";

    //making Global runnable request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    //SingleTon
    public static MovieApiClient getInstance(){
        if (instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMoviesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMoviesLiveData;
    }

    //1- This Method that we are going o call through the classes
    public void searchMovieApi(String query,int pageNumber ){

        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        // Cancelling the Retrofit call
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);

    }


    // Retrieving data from REST_API by runnable class
    //we have a 2 types of Queries: ID & Search Queries.
    private class RetrieveMoviesRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovies(query,pageNumber).execute();
                if (cancelRequest){
                    return;
                }

                if (response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber == 1){
                        // Sending data tp live data
                        //postValue: used for background Thread
                        //setValue: not for background thread
                        mMoviesLiveData.postValue(list);
                    } else {

                        List<MovieModel> currentMovies = mMoviesLiveData.getValue();
                        currentMovies.addAll(list);
                        mMoviesLiveData.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v(TAG, "Error:  "+error);
                    mMoviesLiveData.postValue(null);
                }
            } catch (IOException e){
                e.printStackTrace();
                mMoviesLiveData.postValue(null);
            }
        }

        //Search Method/ query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return Servicey.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest(){
            Log.v(TAG,"Cancelling Search Request .. ");
            cancelRequest = true;
        }

    }


}
