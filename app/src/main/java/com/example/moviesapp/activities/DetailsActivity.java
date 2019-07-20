package com.example.moviesapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView movieTitle, movieReleaseDate, movieRating,
            moviePlotSynopsis, movieOriginalTitle;
    ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle(R.string.detail_title_bar);

        getMovieInformation();
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

    private void getMovieInformation(){
        Intent intent = getIntent();
        if(intent !=  null){
            intent.getStringExtra("");

            String releaseDate = intent.getStringExtra("releaseDate");
            String plotSynopsis = intent.getStringExtra("plotSynopsis");
            String originalTitle = intent.getStringExtra("originalTitle");
            double rating = intent.getDoubleExtra("rating", 0.0);
            String moviePosterUrl = intent.getStringExtra("moviePosterUrl");
            //String movieId = intent.getStringExtra("movieId");

            movieTitle = findViewById(R.id.movieThumbnail);
            movieTitle.setText(originalTitle);

            movieOriginalTitle = findViewById(R.id.originalTitle);
            movieOriginalTitle.setText(originalTitle);


            movieReleaseDate = findViewById(R.id.releaseDate);
            movieReleaseDate.setText(releaseDate);

            movieRating = findViewById(R.id.rating);
            String ratingValue = Double.toString(rating);
            movieRating.setText(ratingValue);

            moviePoster = findViewById(R.id.movie_poster);
            Picasso.get().load(moviePosterUrl).into(moviePoster);

            moviePlotSynopsis = findViewById(R.id.plotSynopsis);
            moviePlotSynopsis.setText(plotSynopsis);
        }
    }
}
