package com.example.moviesapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moviesapp.adapters.MoviesAdapter;
import com.example.moviesapp.R;
import com.example.moviesapp.models.MoviesResult;
import com.example.moviesapp.networking.MovieData;
import com.example.moviesapp.networking.MovieDataInterface;
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

        if(!appUtility.isInternetAvailable(this)){
            Log.d("tag", "you don't have internet access");
            String errorMessage = getResources().getString(R.string.error_message);

            //load error page fragment

        } else {

            movieData = new MovieData(movieDataInterface);
            movieData.getMovies();

            recyclerView = findViewById(R.id.grid_recycler_view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2,
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);

            moviesResultList = new ArrayList<>();
            moviesAdapter = new MoviesAdapter(moviesResultList);
            recyclerView.setAdapter(moviesAdapter);
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
            //todo: create a background task that calls most popular movies endpoint
            Toast.makeText(this, "Most popular movies here", Toast.LENGTH_SHORT).show();
        } else if (itemSelected == R.id.top_rated){
            //todo: create a background task that calls highest rating movies endpoint
            Toast.makeText(this, "Highest rating movies here", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(List<MoviesResult> moviesResult) {
        this.moviesResultList.addAll(moviesResult);
        moviesAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailedResponse(String errorMessage) {

    }
}
