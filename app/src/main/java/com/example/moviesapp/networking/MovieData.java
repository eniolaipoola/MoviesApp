package com.example.moviesapp.networking;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.moviesapp.models.MoviesModel;
import com.example.moviesapp.models.MoviesResult;
import com.example.moviesapp.utils.APPConstant;
import com.example.moviesapp.utils.APPUtility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieData {

    private APPUtility appUtility;
    private MovieDataInterface movieDataInterface;
    private RetrofitClient retrofitClient;

    public MovieData(MovieDataInterface movieInterface){
        appUtility = new APPUtility();
        this.movieDataInterface = movieInterface;
        retrofitClient = new RetrofitClient();
    }

    public void getMovies(){

        APIService movieApiService = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieApiService.getAllMovies(APPConstant.API_PRIVATE_KEY).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<MoviesResult> result = response.body().getResults();
                        getMovieResponse(result);
                        movieDataInterface.onSuccess(result);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
                // return appropriate error view
                movieDataInterface.onFailedResponse(t.getMessage());
            }
        });
    }

    public void getPopularMovies(String sortBy){
        APIService popularMovieService = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        popularMovieService.getPopularMovies(APPConstant.API_PRIVATE_KEY, sortBy).enqueue(
                new Callback<MoviesModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                        if(response.isSuccessful()){
                            if(response.body() != null){
                                Log.d(APPConstant.DEBUG_TAG, "response result size is " + response.body().getResults().size());
                                Log.d(APPConstant.DEBUG_TAG, "response body is " + response.body().getTotalPages());
                                Log.d(APPConstant.DEBUG_TAG, "response url is " + response.raw());

                                List<MoviesResult> result = response.body().getResults();
                                getMovieResponse(result);
                                movieDataInterface.onSuccess(result);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
                        movieDataInterface.onFailedResponse(t.getMessage());
                    }
                }
        );
    }

    //DO NOT REPEAT YOURSELF
    private void getMovieResponse(List<MoviesResult> moviesResponse){
        if(!moviesResponse.isEmpty()){
            for(int i = 0; i < moviesResponse.size(); i++){
                String posterPath = moviesResponse.get(i).getPosterPath();
                int id = moviesResponse.get(i).getMovieId();
                String releaseDate = moviesResponse.get(i).getReleaseDate();
                double rating = moviesResponse.get(i).getRating();
                String originalTitle = moviesResponse.get(i).getOriginalTitle();
                String plotSynopsis = moviesResponse.get(i).getPlotSynopsis();

                moviesResponse.get(i).setMovieId(id);
                moviesResponse.get(i).setOriginalTitle(originalTitle);
                moviesResponse.get(i).setPlotSynopsis(plotSynopsis);
                moviesResponse.get(i).setRating(rating);
                moviesResponse.get(i).setPosterPath(posterPath);
                moviesResponse.get(i).setReleaseDate(releaseDate);

                //build movie url
                String moviePosterUrl = appUtility.buildMoviePosterUrl(posterPath);
                // set posterUrl in movie class
                moviesResponse.get(i).setMoviePosterUrl(moviePosterUrl);
            }
        }
    }
}
