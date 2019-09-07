package com.example.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.moviesapp.R;
import com.example.moviesapp.adapters.MovieReviewAdapter;
import com.example.moviesapp.fragments.AppErrorViewFragment;
import com.example.moviesapp.fragments.AppLoadingViewFragment;
import com.example.moviesapp.models.MovieReviewModel;
import com.example.moviesapp.networking.APIService;
import com.example.moviesapp.networking.MovieData;
import com.example.moviesapp.networking.MovieDataInterface;
import com.example.moviesapp.networking.RetrofitClient;
import com.example.moviesapp.utils.APPConstant;

import java.util.ArrayList;
import java.util.List;

public class MovieReviewActivity extends AppCompatActivity implements MovieDataInterface.Model.OnMovieReviewFinishedListener{

    MovieData movieData;
    APIService apiService;
    RetrofitClient retrofitClient;
    MovieReviewAdapter movieReviewAdapter;
    List<MovieReviewModel.MovieReviewResult> movieReviewResults;
    RecyclerView recyclerView;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.review_title_bar);

        retrofitClient = new RetrofitClient();
        apiService = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieData = new MovieData(apiService);
        movieReviewResults = new ArrayList<>();

        //pass in movie id using bundle
        Intent intent = getIntent();
        if(intent != null){
            int movieId = intent.getIntExtra("movieId", 0);
            movieData.fetchMovieReview(this, movieId);
            showLoadingView();
        } else {
            showErrorView("Movie id not found");
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        recyclerView = findViewById(R.id.movieReviewRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
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
    public void onMovieReviewSuccessful(List<MovieReviewModel.MovieReviewResult> movieReviewResult) {
        hideFragmentView(AppLoadingViewFragment.class.getName());
        movieReviewAdapter = new MovieReviewAdapter(movieReviewResult);
        if(movieReviewResult.size() == 0){

        }
        movieReviewAdapter.notifyDataSetChanged();
        this.movieReviewResults.addAll(movieReviewResult);
        recyclerView.setAdapter(movieReviewAdapter);

    }

    @Override
    public void onMovieReviewFailed(String errorMessage) {
        showErrorView(errorMessage);
    }

    private void showErrorView(String errorMessage){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AppErrorViewFragment.newInstance(errorMessage).show(fragmentTransaction, AppErrorViewFragment.class.getName());
        fragmentTransaction.show(AppLoadingViewFragment.newInstance(errorMessage));
    }

    private void showLoadingView(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
}
