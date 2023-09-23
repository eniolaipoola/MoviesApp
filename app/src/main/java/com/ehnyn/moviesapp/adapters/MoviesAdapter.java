package com.ehnyn.moviesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ehnyn.moviesapp.R;
import com.ehnyn.moviesapp.models.MoviesResult;
import com.ehnyn.moviesapp.models.OnItemClickedListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieAdapterViewHolder> {
    private List<MoviesResult> moviesResults;
    private OnItemClickedListener onItemClickedListener;

    public MoviesAdapter(List<MoviesResult> moviesResults, OnItemClickedListener onItemClicked){
        this.moviesResults = moviesResults;
        this.onItemClickedListener = onItemClicked;
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
        movieAdapterViewHolder.bindResultToView(movieItem, onItemClickedListener);
    }

    @Override
    public int getItemCount() {
        return moviesResults.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView moviePosterImageView;

        private MovieAdapterViewHolder(final View itemView)
        {
            super(itemView);
            moviePosterImageView = itemView.findViewById(R.id.moviePoster);
        }

        private void bindResultToView(final MoviesResult moviesResult, final OnItemClickedListener onItemClickedListener){
            final String moviePosterUrl = moviesResult.getMoviePosterUrl();
            //build picasso here
            Picasso.get()
                    .load(moviePosterUrl)
                    .placeholder(R.drawable.defaultposter)
                    .error(R.drawable.defaultposter)
                    .into(moviePosterImageView);

            itemView.setOnClickListener(v -> onItemClickedListener.onItemClicked(moviesResult));
        }
    }
}
