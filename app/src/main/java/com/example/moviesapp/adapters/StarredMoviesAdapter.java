package com.example.moviesapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviesapp.R;
import com.example.moviesapp.models.Database.StarredMovies;
import com.example.moviesapp.utils.APPConstant;
import com.example.moviesapp.utils.APPUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StarredMoviesAdapter extends RecyclerView.Adapter<StarredMoviesAdapter.StarredMoviesViewHolder> {

    private List<StarredMovies> starredMovies;
    private APPUtility appUtility;

    public StarredMoviesAdapter(List<StarredMovies> starredMovies) {
        this.starredMovies = starredMovies;
        this.appUtility = new APPUtility();
    }

    @Override
    public void onBindViewHolder(@NonNull StarredMoviesViewHolder starredMoviesViewHolder, int i) {
        final StarredMovies currentMovie = starredMovies.get(i);
        starredMoviesViewHolder.bindResultToView(currentMovie);
    }

    @NonNull
    @Override
    public StarredMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int gridListItem = R.layout.movies_list;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view =  layoutInflater.inflate(gridListItem, viewGroup, false);
        return new StarredMoviesViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return starredMovies.size();
    }

    public class StarredMoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePosterImageView;

        public StarredMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePosterImageView = itemView.findViewById(R.id.moviePoster);
        }

        public void bindResultToView(final StarredMovies starredMovies){
            Log.d(APPConstant.DEBUG_TAG, "starred movie list poster path in the adapter is " + starredMovies.getPosterPath());
            final String moviesPosterPath = starredMovies.getPosterPath();
            String moviePosterUrl = appUtility.buildMoviePosterUrl(moviesPosterPath);
            Picasso.get()
                    .load(moviePosterUrl)
                    .placeholder(R.drawable.defaultposter)
                    .error(R.drawable.defaultposter)
                    .into(moviePosterImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
