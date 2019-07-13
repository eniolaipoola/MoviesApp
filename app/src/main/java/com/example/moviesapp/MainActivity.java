package com.example.moviesapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moviesapp.models.MoviesModel;
import com.example.moviesapp.networking.APIService;
import com.example.moviesapp.networking.RetrofitClient;
import com.example.moviesapp.utils.APPConstant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIService movieApiService;
    RetrofitClient retrofitClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofitClient = new RetrofitClient();

        //todo: display a loading view while url call is being made
        getMovies();

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

    public void getMovies(){
        movieApiService = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieApiService.getAllMovies(APPConstant.API_PRIVATE_KEY).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                //todo: extract result node alone without needing the model class
                Log.d(APPConstant.DEBUG_TAG, "response body is " + response.body().getResults().size());
                Log.d(APPConstant.DEBUG_TAG, "response body is " + response.body().getTotalPages());
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {

            }
        });
    }
}
