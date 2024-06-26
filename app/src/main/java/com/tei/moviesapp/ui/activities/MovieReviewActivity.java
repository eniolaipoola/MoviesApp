package com.tei.moviesapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tei.moviesapp.ui.adapters.MovieReviewAdapter;
import com.tei.moviesapp.ui.fragments.AppErrorViewFragment;
import com.tei.moviesapp.ui.fragments.AppLoadingViewFragment;
import com.tei.moviesapp.models.MovieReviewModel;
import com.tei.moviesapp.networking.APIService;
import com.tei.moviesapp.networking.MovieData;
import com.tei.moviesapp.networking.MovieDataInterface;
import com.tei.moviesapp.networking.RetrofitClient;
import com.tei.moviesapp.utils.APPConstant;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tei.moviesapp.R;

public class MovieReviewActivity extends AppCompatActivity implements MovieDataInterface.Model.OnMovieReviewFinishedListener{

    MovieData movieData;
    APIService apiService;
    RetrofitClient retrofitClient;
    MovieReviewAdapter movieReviewAdapter;
    List<MovieReviewModel.MovieReviewResult> movieReviewResults;
    FragmentTransaction fragmentTransaction;
    @BindView(R.id.movieReviewRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.no_review_error)
    TextView movieReviewEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review);
        ButterKnife.bind(this);

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
            showErrorView(getString(R.string.movie_id_error));
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
            movieReviewEmpty.setVisibility(View.VISIBLE);
            movieReviewEmpty.setText(R.string.no_review_error_text);
            showErrorView(getString(R.string.no_review_error_text));
        } else {
            movieReviewAdapter.notifyDataSetChanged();
            this.movieReviewResults.addAll(movieReviewResult);
            recyclerView.setAdapter(movieReviewAdapter);
        }
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
