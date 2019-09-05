package com.example.moviesapp.networking;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.moviesapp.models.MovieReviewModel;
import com.example.moviesapp.models.MovieTrailerModel;
import com.example.moviesapp.models.MoviesModel;
import com.example.moviesapp.models.MoviesResult;
import com.example.moviesapp.utils.APPConstant;
import com.example.moviesapp.utils.APPUtility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieData implements MovieDataInterface.Model {

    private APPUtility appUtility;
    private APIService apiService;
    private RetrofitClient retrofitClient;

    public MovieData(APIService apiService){
        this.appUtility = new APPUtility();
        this.apiService = apiService;
        retrofitClient = new RetrofitClient();
    }

    @Override
    public void getMovies(final OnMoviesFetchedListener moviesFetchedListener) {
        apiService.getAllMovies(APPConstant.API_PRIVATE_KEY).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<MoviesResult> result = response.body().getResults();
                        getMovieResponse(result);
                        moviesFetchedListener.onMoviesSuccessful(result);
                    } else {
                        moviesFetchedListener.onMoviesFailed(response.message());
                    }
                } else {
                    moviesFetchedListener.onMoviesFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
                // return appropriate error view
                moviesFetchedListener.onMoviesFailed(t.getMessage());
            }
        });
    }

    @Override
    public void getPopularMovies(final OnPopularMoviesFetchedListener popularMoviesFetchedListener, String sortOrder) {
        apiService.getPopularMovies(APPConstant.API_PRIVATE_KEY, sortOrder).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<MoviesResult> result = response.body().getResults();
                        if(result != null){
                            getMovieResponse(result);
                            popularMoviesFetchedListener.onPopularMoviesSuccessful(result);
                        } else {
                            popularMoviesFetchedListener.onPopularMoviesFailed("No popular movies found");
                        }
                    } else {
                        popularMoviesFetchedListener.onPopularMoviesFailed(response.message());
                    }
                } else {
                    popularMoviesFetchedListener.onPopularMoviesFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
                popularMoviesFetchedListener.onPopularMoviesFailed(t.getMessage());
            }
        });

    }

    @Override
    public void fetchMovieTrailer(final OnMovieTrailerFinishedListener movieTrailerFinishedListener, final int movieId) {
        final APIService movieTrailer = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieTrailer.getMovieTrailer(movieId, APPConstant.API_PRIVATE_KEY).enqueue(new Callback<MovieTrailerModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieTrailerModel> call, @NonNull Response<MovieTrailerModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<MovieTrailerModel.TrailerResult> movieTrailer = response.body().getResults();
                        Log.d(APPConstant.DEBUG_TAG, "MOVIE SIZE IN DATA CLASS" + movieTrailer.size());
                        if(movieTrailer != null){
                            for(int i = 0; i < movieTrailer.size(); i++){
                                String id = movieTrailer.get(i).getId();
                                String iso_639_1 = movieTrailer.get(i).getIso_639_1();
                                String iso_3166_1 = movieTrailer.get(i).getIso_3166_1();
                                String key = movieTrailer.get(i).getKey();
                                String name = movieTrailer.get(i).getName();
                                String size = movieTrailer.get(i).getSize();
                                String type = movieTrailer.get(i).getType();

                                movieTrailer.get(i).setId(id);
                                movieTrailer.get(i).setIso_639_1(iso_639_1);
                                movieTrailer.get(i).setIso_3166_1(iso_3166_1);
                                movieTrailer.get(i).setKey(key);
                                movieTrailer.get(i).setName(name);
                                movieTrailer.get(i).setSize(size);
                                movieTrailer.get(i).setType(type);
                            }
                            movieTrailerFinishedListener.onMovieTrailerSuccessful(movieTrailer);
                        } else {
                            movieTrailerFinishedListener.onMovieTrailerFailed("No trailer was found for this movie");
                        }
                    } else {
                        movieTrailerFinishedListener.onMovieTrailerFailed(response.message());
                    }
                } else {
                    movieTrailerFinishedListener.onMovieTrailerFailed(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieTrailerModel> call, @NonNull Throwable t) {
                movieTrailerFinishedListener.onMovieTrailerFailed(t.getMessage());
            }
        });

    }

    @Override
    public void fetchMovieReview(final OnMovieReviewFinishedListener movieReviewFinishedListener, int movieId) {
        APIService movieReview = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieReview.getMovieReview(movieId, APPConstant.API_PRIVATE_KEY).enqueue(new Callback<MovieReviewModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieReviewModel> call, @NonNull Response<MovieReviewModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        MovieReviewModel movieReview = response.body();
                        int id = movieReview.getId();
                        String page = movieReview.getPage();
                        movieReview.setId(id);
                        movieReview.setPage(page);
                        List<MovieReviewModel.MovieReviewResult> movieReviewResults = response.body().getReviewResult();
                        movieReviewFinishedListener.onMovieReviewSuccessful(movieReviewResults);

                    } else {
                        movieReviewFinishedListener.onMovieReviewFailed(response.message());
                    }
                } else {
                    movieReviewFinishedListener.onMovieReviewFailed(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieReviewModel> call, @NonNull Throwable t) {
                movieReviewFinishedListener.onMovieReviewFailed(t.getMessage());
            }
        });
    }

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
