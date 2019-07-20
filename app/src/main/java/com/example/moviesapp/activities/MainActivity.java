package com.example.moviesapp.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviesapp.adapters.MoviesAdapter;
import com.example.moviesapp.R;
import com.example.moviesapp.fragments.AppErrorViewFragment;
import com.example.moviesapp.fragments.AppLoadingViewFragment;
import com.example.moviesapp.models.MoviesResult;
import com.example.moviesapp.networking.MovieData;
import com.example.moviesapp.networking.MovieDataInterface;
import com.example.moviesapp.utils.APPConstant;
import com.example.moviesapp.utils.APPUtility;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MovieDataInterface {
    RecyclerView recyclerView;
    APPUtility appUtility;
    List<MoviesResult>  moviesResultList;
    private MoviesAdapter moviesAdapter;
    MovieDataInterface movieDataInterface;
    MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appUtility = new APPUtility();
        movieDataInterface = this;
        moviesResultList = new ArrayList<>();
        movieData = new MovieData(movieDataInterface);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(!appUtility.isInternetAvailable(this)){
            //show error view
            String errorMessage = getResources().getString(R.string.internet_error_message);
            AppErrorViewFragment.newInstance(errorMessage).show(fragmentTransaction, AppErrorViewFragment.class.getName());
            fragmentTransaction.show(AppLoadingViewFragment.newInstance(errorMessage));
            Log.d("tag", "you don't have internet access");

        } else {
            movieData.getMovies();
            AppLoadingViewFragment.newInstance("Loading").show(fragmentTransaction,
                    AppLoadingViewFragment.class.getName());      //show loading view

            recyclerView = findViewById(R.id.grid_recycler_view);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2,
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);
        }
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
            //make api call
            movieData.getPopularMovies(APPConstant.SORT_BY_POPULAR);

        } else if (itemSelected == R.id.top_rated){
            //make url call
            movieData = new MovieData(movieDataInterface);
            movieData.getPopularMovies(APPConstant.SORT_BY_TOP_RATED);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onSuccess(List<MoviesResult> moviesResult) {

        moviesAdapter = new MoviesAdapter(moviesResult);
        this.moviesResultList.addAll(moviesResult);
        moviesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(moviesAdapter);

        //remove loading view on success
        removeDialogFragment(getSupportFragmentManager(), AppLoadingViewFragment.class.getName());

    }

    @Override
    public void onFailedResponse(String errorMessage) {
        //show error view on failure
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AppErrorViewFragment.newInstance(errorMessage).show(fragmentTransaction, AppErrorViewFragment.class.getName());
        fragmentTransaction.show(AppLoadingViewFragment.newInstance(errorMessage));

    }

    private void removeDialogFragment(FragmentManager fragmentManager, String fragmentTag) {
        Fragment dialogFragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (dialogFragment != null) {
            fragmentManager.beginTransaction().
                    remove(dialogFragment).commit();
        }
    }
}
