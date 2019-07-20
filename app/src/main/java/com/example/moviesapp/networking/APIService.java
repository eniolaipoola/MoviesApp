package com.example.moviesapp.networking;

import com.example.moviesapp.models.MoviesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("movie/popular")
    Call<MoviesModel> getAllMovies(
            @Query("api_key")  String apiKey
    );

    @GET("discover/movie")
    Call<MoviesModel> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("sort_by") String sortBy
    );
}
