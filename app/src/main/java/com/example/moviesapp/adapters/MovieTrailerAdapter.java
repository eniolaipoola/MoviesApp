package com.example.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    Context mContext;

    public MovieTrailerAdapter(List<MovieTrailerModel.TrailerResult> trailerResultList, Context context) {
        this.movieTrailerResult = trailerResultList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieTrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view =  layoutInflater.inflate(R.layout.movie_trailer, viewGroup, false);
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
            movieTrailerName = itemView.findViewById(R.id.movieTrailerName);
        }

        private void bindResultToView(final MovieTrailerModel.TrailerResult trailerResult){

            String name = trailerResult.getName();
            movieTrailerName.setText(name);
            movieTrailerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://www.youtube.com/";
                    Uri webPage = Uri.parse(url);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, webPage);
                    if(browserIntent.resolveActivity(mContext.getPackageManager()) != null){
                        mContext.startActivity(browserIntent);
                    }
                }
            });
        }
    }
}
