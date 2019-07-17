package com.example.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviesapp.R;
import com.example.moviesapp.activities.DetailsActivity;
import com.example.moviesapp.models.MoviesResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieAdapterViewHolder> {
    private List<MoviesResult> moviesResults;


    public MoviesAdapter(List<MoviesResult> moviesResults){
        this.moviesResults = moviesResults;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int gridListItem = R.layout.movies_list;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view =  layoutInflater.inflate(gridListItem, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        final MoviesResult movieItem = moviesResults.get(position);
        final String moviePosterUrl = movieItem.getMoviePosterUrl();

        final String releaseDate = movieItem.getReleaseDate();
        final double rating = movieItem.getRating();
        final String originalTitle = movieItem.getOriginalTitle();
        final String plotSynopsis = movieItem.getPlotSynopsis();
        final int movieId = movieItem.getMovieId();
        Log.d("tag", "releaseDate is  " + releaseDate);
        Log.d("tag", "poster url is  " + moviePosterUrl);
        Log.d("tag", "rating is  " + rating);
        Log.d("tag", "original title is  " + originalTitle);
        Log.d("tag", "plotsynopsis is is  " + plotSynopsis);
        Log.d("tag", "movieId is  " + movieId);

        //build picasso here
        Picasso.get().load(moviePosterUrl).into(movieAdapterViewHolder.moviePosterImageView);
        movieAdapterViewHolder.moviePosterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("releaseDate", releaseDate);
                intent.putExtra("plotSynopsis", plotSynopsis);
                intent.putExtra("originalTitle", originalTitle);
                intent.putExtra("rating", rating);
                intent.putExtra("moviePosterUrl", moviePosterUrl);
                intent.putExtra("movieId", movieId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(moviesResults == null){
            return 0;
        }
        return moviesResults.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView moviePosterImageView;

        public MovieAdapterViewHolder(final View view)
        {
            super(view);
            moviePosterImageView = view.findViewById(R.id.moviePoster);
        }
    }
}
