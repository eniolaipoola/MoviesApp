package com.tei.moviesapp.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tei.moviesapp.R;
import com.tei.moviesapp.ui.adapters.MoviesAdapter;
import com.tei.moviesapp.ui.fragments.AppErrorViewFragment;
import com.tei.moviesapp.ui.fragments.AppLoadingViewFragment;
import com.tei.moviesapp.ui.MainViewModel;
import com.tei.moviesapp.models.MoviesResult;
import com.tei.moviesapp.models.OnItemClickedListener;
import com.tei.moviesapp.networking.APIService;
import com.tei.moviesapp.networking.MovieData;
import com.tei.moviesapp.networking.MovieDataInterface;
import com.tei.moviesapp.networking.RetrofitClient;
import com.tei.moviesapp.utils.APPConstant;
import com.tei.moviesapp.utils.APPUtility;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MovieDataInterface.Model.OnMoviesFetchedListener,
        MovieDataInterface.Model.OnPopularMoviesFetchedListener, OnItemClickedListener  {

    APPUtility appUtility;
    List<MoviesResult>  moviesResultList;
    private MoviesAdapter moviesAdapter;
    MovieData movieData;
    Context mContext;
    APIService apiService;
    RetrofitClient retrofitClient;

    @BindView(value = R.id.grid_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        appUtility = new APPUtility();
        mContext = MainActivity.this;
        moviesResultList = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
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
            MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            viewModel.getMoviesResult().observe(this, moviesResults -> setStarredMovies(moviesResults));
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
        recyclerView.setAdapter(moviesAdapter);
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
        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra("MOVIE", movieResult);
        intent.putExtra("movieId", movieResult.getId());
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
