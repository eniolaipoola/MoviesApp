package com.tei.moviesapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tei.moviesapp.ui.adapters.MovieTrailerAdapter;
import com.tei.moviesapp.ui.fragments.AppErrorViewFragment;
import com.tei.moviesapp.ui.fragments.AppLoadingViewFragment;
import com.tei.moviesapp.models.Database.AppDatabase;
import com.tei.moviesapp.models.Database.AppExecutor;
import com.tei.moviesapp.models.MovieTrailerModel;
import com.tei.moviesapp.models.MoviesResult;
import com.tei.moviesapp.networking.APIService;
import com.tei.moviesapp.networking.MovieData;
import com.tei.moviesapp.networking.MovieDataInterface;
import com.tei.moviesapp.networking.RetrofitClient;
import com.tei.moviesapp.utils.APPConstant;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Date;
import com.tei.moviesapp.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements
        MovieDataInterface.Model.OnMovieTrailerFinishedListener {

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

    //ButteKnife to bind views
    @BindView(R.id.releaseDate)
    TextView movieReleaseDate;
    @BindView(R.id.originalTitle)
    TextView movieOriginalTitle;
    @BindView(R.id.rating)
    TextView movieRating;
    @BindView(R.id.plotSynopsis)
    TextView moviePlotSynopsis;
    @BindView(R.id.reviewContent)
    Button reviewContent;
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.starMovie)
    ImageView starredMovieImageView;

    //Member variable for the database
    private AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.detail_title_bar);

        mContext = this;
        retrofitClient = new RetrofitClient();
        apiService = retrofitClient.getRetrofit(APPConstant.API_BASE_URL).create(APIService.class);
        movieData = new MovieData(apiService);
        movieTrailerList = new ArrayList<>();
        final String url = "https://www.youtube.com/";


        recyclerView = findViewById(R.id.movieTrailerRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getMovieInformation();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fetch movie review
        movieData.fetchMovieTrailer(this, movieId);
        showLoadingView();

        mDatabase = AppDatabase.getInstance(getApplicationContext());
        starredMovieImageView.setOnClickListener(v -> {
            saveStarredMovie(movieId, releaseDate, rating, moviePosterUrl, originalTitle, plotSynopsis,
                    moviePosterUrl, 1, new Date(), new Date());
            Toast.makeText(DetailsActivity.this, "This movie is starred successfully", Toast.LENGTH_LONG).show();
        });

        reviewContent.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MovieReviewActivity.class);
            intent.putExtra("movieId", movieId);
            startActivity(intent);
        });

        recyclerView.setOnClickListener(v -> openWebPage(url));
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void saveStarredMovie(final int movieId, String release_date, double movieRating, String posterPath,
                                 String originalTitle, String plotSynopsis, String moviePosterUrl, int starred, Date createdAt, Date updatedAt) {

        final MoviesResult moviesResult = new MoviesResult(movieId, release_date, movieRating, posterPath, originalTitle,
                plotSynopsis, moviePosterUrl, starred, createdAt, updatedAt);
        AppExecutor.getInstance().diskIO().execute(() -> mDatabase.movieDao().saveMovie(moviesResult));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        if (itemSelected == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieTrailerSuccessful(List<MovieTrailerModel.TrailerResult> movieTrailer) {
        hideFragmentView(AppLoadingViewFragment.class.getName());
        movieTrailerAdapter = new MovieTrailerAdapter(movieTrailer, this);
        movieTrailerAdapter.notifyDataSetChanged();
        this.movieTrailerList.addAll(movieTrailer);
        recyclerView.setAdapter(movieTrailerAdapter);
    }

    @Override
    public void onMovieTrailerFailed(String errorMessage) {
        hideFragmentView(AppLoadingViewFragment.class.getName());
        showErrorView(errorMessage);
    }

    private void showErrorView(String errorMessage) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AppErrorViewFragment.newInstance(errorMessage).show(fragmentTransaction, AppErrorViewFragment.class.getName());
        fragmentTransaction.show(AppLoadingViewFragment.newInstance(errorMessage));
    }

    private void showLoadingView() {
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

    private void getMovieInformation() {
        Intent intent = getIntent();
        if (intent != null) {
            MoviesResult moviesResult = (MoviesResult) intent.getSerializableExtra("MOVIE");

            releaseDate = moviesResult.getReleaseDate();
            plotSynopsis = moviesResult.getPlotSynopsis();
            movieId = moviesResult.getId();
            originalTitle = moviesResult.getOriginalTitle();
            moviePosterUrl = moviesResult.getMoviePosterUrl();
            movieReleaseDate.setText(releaseDate);
            moviePlotSynopsis.setText(plotSynopsis);
            movieOriginalTitle.setText(originalTitle);
            rating = moviesResult.getRating();
            ratingValue = Double.toString(rating);
            movieRating.setText(ratingValue);
            moviePoster = findViewById(R.id.movie_poster);
            Picasso.get().load(moviePosterUrl).into(moviePoster);
        }
    }
}
