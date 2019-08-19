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

        final APIService movieApiService = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieApiService.getAllMovies(APPConstant.API_PRIVATE_KEY).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<MoviesResult> result = response.body().getResults();
                        getMovieResponse(result);
                        movieDataInterface.onSuccess(result);
                    } else {
                        movieDataInterface.onFailedResponse(response.message());
                    }
                } else {
                    movieDataInterface.onFailedResponse(response.message());
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
                                List<MoviesResult> result = response.body().getResults();
                                if(result != null){
                                    getMovieResponse(result);
                                    movieDataInterface.onSuccess(result);
                                } else {
                                    movieDataInterface.onFailedResponse("No popular movies found");
                                }
                            } else {
                                movieDataInterface.onFailedResponse(response.message());
                            }
                        } else {
                            movieDataInterface.onFailedResponse(response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
                        movieDataInterface.onFailedResponse(t.getMessage());
                    }
                }
        );
    }


    public void fetchMovieTrailer(final int movieId){
        APIService movieTrailer = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieTrailer.getMovieTrailer(movieId, APPConstant.API_PRIVATE_KEY).enqueue(new Callback<MovieTrailerModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieTrailerModel> call, @NonNull Response<MovieTrailerModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<MovieTrailerModel.TrailerResult> movieTrailer = response.body().getResults();
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
                        } else {
                            movieDataInterface.onFailedResponse("No trailer was found for this movie");
                        }
                    } else {
                        movieDataInterface.onFailedResponse(response.message());
                    }
                } else {
                    movieDataInterface.onFailedResponse(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieTrailerModel> call, @NonNull Throwable t) {
                movieDataInterface.onFailedResponse(t.getMessage());
            }
        });

    }

    public void fetchMovieReview(int movieId){
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
                        if(movieReviewResults != null){
                            for(int i = 0; i < movieReviewResults.size(); i++){
                                String author = movieReviewResults.get(i).getAuthor();
                                String resultId = movieReviewResults.get(i).getId();
                                String content = movieReviewResults.get(i).getContent();
                                String url = movieReviewResults.get(i).getUrl();

                                movieReviewResults.get(i).setAuthor(author);
                                movieReviewResults.get(i).setContent(content);
                                movieReviewResults.get(i).setId(resultId);
                                movieReviewResults.get(i).setUrl(url);

                            }
                        } else {
                            movieDataInterface.onFailedResponse("No review found");
                        }

                    } else {
                        movieDataInterface.onFailedResponse(response.message());
                    }
                } else {
                    movieDataInterface.onFailedResponse(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieReviewModel> call, @NonNull Throwable t) {
                movieDataInterface.onFailedResponse(t.getMessage());
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
                Log.d(APPConstant.DEBUG_TAG, "MOVIE POSTER URL" + moviePosterUrl);
            }
        }
    }
}
