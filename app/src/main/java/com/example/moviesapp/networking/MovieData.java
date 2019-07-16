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

    public MovieData(MovieDataInterface movieInterface){
        appUtility = new APPUtility();
        this.movieDataInterface = movieInterface;
    }

    public void getMovies(){
        RetrofitClient retrofitClient = new RetrofitClient();
        APIService movieApiService = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);

        movieApiService.getAllMovies(APPConstant.API_PRIVATE_KEY).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Log.d(APPConstant.DEBUG_TAG, "response result size is " + response.body().getResults().size());
                        Log.d(APPConstant.DEBUG_TAG, "response body is " + response.body().getTotalPages());

                        List<MoviesResult> result = response.body().getResults();
                        if(!result.isEmpty()){
                            for(int i = 0; i < result.size(); i++){
                                String posterPath = result.get(i).getPosterPath();
                                int id = result.get(i).getMovieId();
                                String releaseDate = result.get(i).getReleaseDate();
                                double rating = result.get(i).getRating();
                                String originalTitle = result.get(i).getOriginalTitle();
                                String plotSynopsis = result.get(i).getPlotSynopsis();


                                result.get(i).setMovieId(id);
                                result.get(i).setOriginalTitle(originalTitle);
                                result.get(i).setPlotSynopsis(plotSynopsis);
                                result.get(i).setRating(rating);
                                result.get(i).setPosterPath(posterPath);
                                result.get(i).setReleaseDate(releaseDate);

                                //build movie url
                                String moviePosterUrl = appUtility.buildMoviePosterUrl(posterPath);

                                // set posterUrl in movie class
                                result.get(i).setMoviePosterUrl(moviePosterUrl);
                            }
                        }

                        movieDataInterface.onSuccess(result);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
                // return appropriate error view
                String errorMessage = t.getMessage();
                movieDataInterface.onFailedResponse(errorMessage);
            }
        });
    }
}
