package com.example.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.R;
import com.example.moviesapp.adapters.MovieTrailerAdapter;
import com.example.moviesapp.fragments.AppErrorViewFragment;
import com.example.moviesapp.fragments.AppLoadingViewFragment;
import com.example.moviesapp.models.Database.AppDatabase;
import com.example.moviesapp.models.Database.AppExecutor;
import com.example.moviesapp.models.MovieTrailerModel;
import com.example.moviesapp.models.MoviesResult;
import com.example.moviesapp.networking.APIService;
import com.example.moviesapp.networking.MovieData;
import com.example.moviesapp.networking.MovieDataInterface;
import com.example.moviesapp.networking.RetrofitClient;
import com.example.moviesapp.utils.APPConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements
        MovieDataInterface.Model.OnMovieTrailerFinishedListener {

    TextView movieTitle, movieReleaseDate, movieRating,
            moviePlotSynopsis, movieOriginalTitle;
    ImageView moviePoster, starredMovieImageView;
    Context mContext;
    FragmentTransaction fragmentTransaction;
    MovieData movieData;
    APIService apiService;
    RetrofitClient retrofitClient;
    private MovieTrailerAdapter movieTrailerAdapter;
    List<MovieTrailerModel.TrailerResult> movieTrailerList;
    RecyclerView recyclerView;
    String originalTitle, releaseDate, plotSynopsis, ratingValue, moviePosterUrl;
    int movieId;
    double rating;
    Button reviewContent;

    //Member variable for the database
    private AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.detail_title_bar);

        mContext = this;
        retrofitClient = new RetrofitClient();
        apiService = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieData = new MovieData(apiService);
        movieTrailerList = new ArrayList<>();


        recyclerView = findViewById(R.id.movieTrailerRecyclerView);
        starredMovieImageView = findViewById(R.id.starMovie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getMovieInformation();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fetch movie review
        movieData.fetchMovieTrailer(this, movieId);
        showLoadingView();

        mDatabase = AppDatabase.getInstance(getApplicationContext());
        starredMovieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStarredMovie(movieId,releaseDate, rating, moviePosterUrl, originalTitle, plotSynopsis,
                        moviePosterUrl,1, new Date(), new Date());
                Toast.makeText(DetailsActivity.this, "This movie is starred successfully", Toast.LENGTH_LONG).show();
            }
        });

        reviewContent = findViewById(R.id.reviewContent);
        reviewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieReviewActivity.class);
                intent.putExtra("movieId", movieId);
                startActivity(intent);
            }
        });
    }

    public void saveStarredMovie(final int movieId, String release_date, double movieRating, String posterPath,
                                 String originalTitle, String plotSynopsis, String moviePosterUrl, int starred, Date createdAt, Date updatedAt){

        final MoviesResult moviesResult = new MoviesResult(movieId, release_date, movieRating, posterPath, originalTitle,
                plotSynopsis, moviePosterUrl, starred, createdAt, updatedAt);
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.movieDao().saveMovie(moviesResult);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        if (itemSelected == android.R.id.home){
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieTrailerSuccessful(List<MovieTrailerModel.TrailerResult> movieTrailer) {
        hideFragmentView(AppLoadingViewFragment.class.getName());
        movieTrailerAdapter = new MovieTrailerAdapter(movieTrailer);
        movieTrailerAdapter.notifyDataSetChanged();
        this.movieTrailerList.addAll(movieTrailer);
        recyclerView.setAdapter(movieTrailerAdapter);

    }

    @Override
    public void onMovieTrailerFailed(String errorMessage) {
        hideFragmentView(AppLoadingViewFragment.class.getName());
        showErrorView(errorMessage);
    }

    private void showErrorView(String errorMessage){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AppErrorViewFragment.newInstance(errorMessage).show(fragmentTransaction, AppErrorViewFragment.class.getName());
        fragmentTransaction.show(AppLoadingViewFragment.newInstance(errorMessage));
    }

    private void showLoadingView(){
        AppLoadingViewFragment.newInstance("Loading").show(fragmentTransaction,
                AppLoadingViewFragment.class.getName());
    }

    private void hideFragmentView(String fragmentTag) {
        Fragment dialogFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (dialogFragment != null) {
            getSupportFragmentManager().beginTransaction().
                    remove(dialogFragment).commit();
        }
    }

    private void getMovieInformation(){
        Intent intent = getIntent();
        if(intent !=  null){
            releaseDate = intent.getStringExtra("releaseDate");
            movieReleaseDate = findViewById(R.id.releaseDate);
            movieReleaseDate.setText(releaseDate);

            plotSynopsis = intent.getStringExtra("plotSynopsis");
            moviePlotSynopsis = findViewById(R.id.plotSynopsis);
            moviePlotSynopsis.setText(plotSynopsis);

            originalTitle = intent.getStringExtra("originalTitle");
            movieTitle = findViewById(R.id.movieThumbnail);
            movieTitle.setText(originalTitle);
            movieOriginalTitle = findViewById(R.id.originalTitle);
            movieOriginalTitle.setText(originalTitle);

            rating = intent.getDoubleExtra("rating", 0.0);
            movieRating = findViewById(R.id.rating);
            ratingValue = Double.toString(rating);
            movieRating.setText(ratingValue);

            moviePosterUrl = intent.getStringExtra("moviePosterUrl");
            movieId = intent.getIntExtra("movieId", 0);
            moviePoster = findViewById(R.id.movie_poster);
            Picasso.get().load(moviePosterUrl).into(moviePoster);
        }
    }
}
