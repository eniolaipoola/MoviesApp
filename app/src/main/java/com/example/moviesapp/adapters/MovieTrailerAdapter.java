package com.example.moviesapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.models.MovieTrailerModel;

import java.util.List;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerAdapterViewHolder> {

    private List<MovieTrailerModel.TrailerResult> movieTrailerResult;

    public MovieTrailerAdapter(List<MovieTrailerModel.TrailerResult> trailerResultList) {
        this.movieTrailerResult = trailerResultList;
    }

    @NonNull
    @Override
    public MovieTrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view =  layoutInflater.inflate(R.layout.movie_reviews, viewGroup, false);
        return new MovieTrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerAdapterViewHolder movieTrailerAdapterViewHolder, int position) {
        final MovieTrailerModel.TrailerResult trailerResult = movieTrailerResult.get(position);
        movieTrailerAdapterViewHolder.bindResultToView(trailerResult);
    }

    @Override
    public int getItemCount() {
        return movieTrailerResult.size();
    }

    public class MovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView movieTrailerName;

        MovieTrailerAdapterViewHolder(final View itemView) {
            super(itemView);
            movieTrailerName = itemView.findViewById(R.id.movieReviewName);
        }

        private void bindResultToView(final MovieTrailerModel.TrailerResult trailerResult){

            String name = trailerResult.getName();
            movieTrailerName.setText(name);
        }
    }
}