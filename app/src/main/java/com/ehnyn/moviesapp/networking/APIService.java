package com.ehnyn.moviesapp.networking;

import com.ehnyn.moviesapp.models.MovieReviewModel;
import com.ehnyn.moviesapp.models.MovieTrailerModel;
import com.ehnyn.moviesapp.models.MoviesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("movie/{id}/videos")
    Call<MovieTrailerModel> getMovieTrailer(
      @Path("id") int movieId,
      @Query("api_key") String apiKey
    );


    @GET("movie/{id}/reviews")
    Call<MovieReviewModel> getMovieReview(
            @Path("id") int movieId,
            @Query("api_key") String apiKey
    );
}
