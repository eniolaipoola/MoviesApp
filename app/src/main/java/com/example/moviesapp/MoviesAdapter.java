package com.example.moviesapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        String moviePosterUrl = movieItem.getMoviePosterUrl();
        Log.d("tag", "poster url is  " + moviePosterUrl);

        //build picasso here
        Picasso.get().load(moviePosterUrl).into(movieAdapterViewHolder.moviePosterImageView);

    }

    @Override
    public int getItemCount() {
        return moviesResults.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView moviePosterImageView;

        public MovieAdapterViewHolder(View view)
        {
            super(view);
           /* moviePosterImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //launch detail activity
                }
            });
*/
            moviePosterImageView = view.findViewById(R.id.moviePoster);
        }
    }
}
