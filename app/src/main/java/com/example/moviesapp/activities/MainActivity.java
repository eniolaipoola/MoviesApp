package com.example.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviesapp.adapters.MoviesAdapter;
import com.example.moviesapp.R;
import com.example.moviesapp.fragments.AppErrorViewFragment;
import com.example.moviesapp.fragments.AppLoadingViewFragment;
import com.example.moviesapp.models.MovieTrailerModel;
import com.example.moviesapp.models.MoviesResult;
import com.example.moviesapp.models.OnItemClickedListener;
import com.example.moviesapp.networking.MovieData;
import com.example.moviesapp.networking.MovieDataInterface;
import com.example.moviesapp.utils.APPConstant;
import com.example.moviesapp.utils.APPUtility;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MovieDataInterface, OnItemClickedListener  {
    RecyclerView recyclerView;
    APPUtility appUtility;
    List<MoviesResult>  moviesResultList;
    private MoviesAdapter moviesAdapter;
    MovieDataInterface movieDataInterface;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appUtility = new APPUtility();
        movieDataInterface = this;
        moviesResultList = new ArrayList<>();
        movieData = new MovieData(movieDataInterface);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(!appUtility.isInternetAvailable(this)){
            String errorMessage = getResources().getString(R.string.internet_error_message);
            showErrorView(errorMessage);
        } else {
            movieData.getMovies();
            showLoadingView();
            recyclerView = findViewById(R.id.grid_recycler_view);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, calculateNumberOfColumns(this),
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);
        }
    }

    public static int calculateNumberOfColumns(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2){
            noOfColumns = 2;
        }
        Log.d(APPConstant.DEBUG_TAG, "No of columns is: " + noOfColumns);
        return  noOfColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        if(itemSelected == R.id.most_popular){
            movieData.getPopularMovies(APPConstant.SORT_BY_POPULAR);
        } else if (itemSelected == R.id.top_rated){
            movieData = new MovieData(movieDataInterface);
            movieData.getPopularMovies(APPConstant.SORT_BY_TOP_RATED);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onSuccess(List<MoviesResult> moviesResult) {
        removeDialogFragment(AppLoadingViewFragment.class.getName());
        moviesAdapter = new MoviesAdapter(moviesResult, this);
        this.moviesResultList.addAll(moviesResult);
        Log.d(APPConstant.DEBUG_TAG, "movie result " + moviesResult.get(1));
        moviesAdapter.notifyDataSetChanged();
        if(moviesAdapter != null){
            recyclerView.setAdapter(moviesAdapter);
        }
    }

    @Override
    public void onFailedResponse(String errorMessage) {
        removeDialogFragment(AppLoadingViewFragment.class.getName());
        showErrorView(errorMessage);
    }

    @Override
    public void onItemClicked(MoviesResult movieResult) {
        final String moviePosterUrl = movieResult.getMoviePosterUrl();
        final String releaseDate = movieResult.getReleaseDate();
        final double rating = movieResult.getRating();
        final String originalTitle = movieResult.getOriginalTitle();
        final String plotSynopsis = movieResult.getPlotSynopsis();
        final int movieId = movieResult.getMovieId();

        Context context = MainActivity.this;
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("releaseDate", releaseDate);
        intent.putExtra("plotSynopsis", plotSynopsis);
        intent.putExtra("originalTitle", originalTitle);
        intent.putExtra("rating", rating);
        intent.putExtra("moviePosterUrl", moviePosterUrl);
        intent.putExtra("movieId", movieId);
        context.startActivity(intent);
    }

    private void removeDialogFragment(String fragmentTag) {
        Fragment dialogFragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (dialogFragment != null) {
            fragmentManager.beginTransaction().
                    remove(dialogFragment).commit();
        }
    }

    private void showLoadingView(){
        AppLoadingViewFragment.newInstance("Loading").show(fragmentTransaction,
                AppLoadingViewFragment.class.getName());
    }

    private void showErrorView(String errorMessage){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AppErrorViewFragment.newInstance(errorMessage).show(fragmentTransaction, AppErrorViewFragment.class.getName());
        fragmentTransaction.show(AppLoadingViewFragment.newInstance(errorMessage));
    }

    @Override
    public void onMovieTrailerSuccessful(List<MovieTrailerModel> movieTrailer) {

    }
}
