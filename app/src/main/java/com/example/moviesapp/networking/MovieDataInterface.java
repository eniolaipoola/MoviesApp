package com.example.moviesapp.networking;

import com.example.moviesapp.models.MovieReviewModel;
import com.example.moviesapp.models.MovieTrailerModel;
import com.example.moviesapp.models.MoviesResult;

import java.util.List;

public interface MovieDataInterface {

    interface Model {

        interface OnMoviesFetchedListener {

            void onMoviesSuccessful(List<MoviesResult> moviesResult);

            void onMoviesFailed(String errorMessage);
        }

        void getMovies(OnMoviesFetchedListener moviesFetchedListener);

        interface OnPopularMoviesFetchedListener {

            void onPopularMoviesSuccessful(List<MoviesResult> moviesResult);

            void onPopularMoviesFailed(String errorMessage);

        }

        void getPopularMovies(OnPopularMoviesFetchedListener popularMoviesFetchedListener, String sortOrder);

        interface OnMovieTrailerFinishedListener {

            void onMovieTrailerSuccessful(List<MovieTrailerModel.TrailerResult> movieTrailer);

            void onMovieTrailerFailed(String errorMessage);
        }

        void fetchMovieTrailer(OnMovieTrailerFinishedListener movieTrailerFinishedListener, int movieId);

        interface OnMovieReviewFinishedListener {
            void onMovieReviewSuccessful(MovieReviewModel.MovieReviewResult movieReviewResult);

            void onMovieReviewFailed(String errorMessage);
        }

        void fetchMovieReview(OnMovieReviewFinishedListener movieReviewFinishedListener, int movieId);

    }

}
