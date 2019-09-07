package com.example.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviesapp.R;
import com.example.moviesapp.adapters.MoviesAdapter;
import com.example.moviesapp.fragments.AppErrorViewFragment;
import com.example.moviesapp.fragments.AppLoadingViewFragment;
import com.example.moviesapp.models.Database.AppDatabase;
import com.example.moviesapp.models.Database.AppExecutor;
import com.example.moviesapp.models.MoviesResult;
import com.example.moviesapp.models.OnItemClickedListener;
import com.example.moviesapp.networking.APIService;
import com.example.moviesapp.networking.MovieData;
import com.example.moviesapp.networking.MovieDataInterface;
import com.example.moviesapp.networking.RetrofitClient;
import com.example.moviesapp.utils.APPConstant;
import com.example.moviesapp.utils.APPUtility;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MovieDataInterface.Model.OnMoviesFetchedListener,
        MovieDataInterface.Model.OnPopularMoviesFetchedListener, OnItemClickedListener  {

    RecyclerView recyclerView;
    APPUtility appUtility;
    List<MoviesResult>  moviesResultList;
    private MoviesAdapter moviesAdapter;
    MovieData movieData;
    Context mContext;
    APIService apiService;
    RetrofitClient retrofitClient;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appUtility = new APPUtility();
        mContext = MainActivity.this;
        moviesResultList = new ArrayList<>();
        appDatabase = AppDatabase.getInstance(getApplicationContext());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.app_name);

        retrofitClient = new RetrofitClient();
        apiService = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieData = new MovieData(apiService);

        if(!appUtility.isInternetAvailable(this)){
            String errorMessage = getResources().getString(R.string.internet_error_message);
            showErrorView(errorMessage);
        } else {
            movieData.getMovies(this);
            showLoadingView();
            recyclerView = findViewById(R.id.grid_recycler_view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, calculateNumberOfColumns(this),
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static int calculateNumberOfColumns(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);

        if(noOfColumns < 2){
            noOfColumns = 2;
        }
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
            setTitle(R.string.most_popular);
            movieData.getPopularMovies(this, APPConstant.SORT_BY_POPULAR);
            showLoadingView();
        } else if (itemSelected == R.id.top_rated){
            setTitle(R.string.top_rated);
            movieData.getPopularMovies(this, APPConstant.SORT_BY_TOP_RATED);
            showLoadingView();
        } else if(itemSelected == R.id.starred_movies){
            setTitle(R.string.starred);
            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    moviesResultList = appDatabase.movieDao().fetchAllMovies();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setStarredMovies(moviesResultList);
                        }
                    });
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    public void setStarredMovies(List<MoviesResult> movies){
        moviesAdapter = new MoviesAdapter(movies, this);
        moviesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public void onMoviesSuccessful(List<MoviesResult> moviesResult) {
        removeDialogFragment(AppLoadingViewFragment.class.getName());
        moviesAdapter = new MoviesAdapter(moviesResult, this);
        this.moviesResultList.addAll(moviesResult);
        moviesAdapter.notifyDataSetChanged();
        if(moviesAdapter != null){
            recyclerView.setAdapter(moviesAdapter);
        }
    }

    @Override
    public void onMoviesFailed(String errorMessage) {
        removeDialogFragment(AppLoadingViewFragment.class.getName());
        showErrorView(errorMessage);
    }

    @Override
    public void onPopularMoviesSuccessful(List<MoviesResult> moviesResult) {
        removeDialogFragment(AppLoadingViewFragment.class.getName());
        moviesAdapter = new MoviesAdapter(moviesResult, this);
        moviesResultList.clear();
        recyclerView.setAdapter(moviesAdapter);
        this.moviesResultList.addAll(moviesResult);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPopularMoviesFailed(String errorMessage) {
        showErrorView("Fetching popular movies failed");
    }

    @Override
    public void onItemClicked(MoviesResult movieResult) {
        final String moviePosterUrl = movieResult.getMoviePosterUrl();
        final String releaseDate = movieResult.getReleaseDate();
        final double rating = movieResult.getRating();
        final String originalTitle = movieResult.getOriginalTitle();
        final String plotSynopsis = movieResult.getPlotSynopsis();
        final int movieId = movieResult.getId();

        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra("releaseDate", releaseDate);
        intent.putExtra("plotSynopsis", plotSynopsis);
        intent.putExtra("originalTitle", originalTitle);
        intent.putExtra("rating", rating);
        intent.putExtra("moviePosterUrl", moviePosterUrl);
        intent.putExtra("movieId", movieId);
        mContext.startActivity(intent);
    }

    private void removeDialogFragment(String fragmentTag) {
        Fragment dialogFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (dialogFragment != null) {
            getSupportFragmentManager().beginTransaction().
                    remove(dialogFragment).commit();
        }
    }

    private void showLoadingView(){
        AppLoadingViewFragment.newInstance("Loading").show(getSupportFragmentManager().beginTransaction(),
                AppLoadingViewFragment.class.getName());
    }

    private void showErrorView(String errorMessage){
        AppErrorViewFragment.newInstance(errorMessage).show(getSupportFragmentManager().beginTransaction(),
                AppErrorViewFragment.class.getName());
        getSupportFragmentManager().beginTransaction().show(AppLoadingViewFragment.newInstance(errorMessage));
    }
}
