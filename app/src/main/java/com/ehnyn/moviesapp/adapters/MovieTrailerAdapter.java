package com.ehnyn.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ehnyn.moviesapp.R;
import com.ehnyn.moviesapp.models.MovieTrailerModel;
import com.squareup.picasso.Picasso;

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
        ImageView movieTrailerThumbnail, playTrailerButton;

        MovieTrailerAdapterViewHolder(final View itemView) {
            super(itemView);
            movieTrailerThumbnail = itemView.findViewById(R.id.trailerThumbnail);
            playTrailerButton = itemView.findViewById(R.id.playButton);
        }

        private void bindResultToView(final MovieTrailerModel.TrailerResult trailerResult){

            String movieKey = trailerResult.getKey();
            String thumbnail = getMovieTrailerThumbnail(movieKey);
            Picasso.get()
                    .load(thumbnail)
                    .placeholder(R.drawable.defaultposter)
                    .error(R.drawable.defaultposter)
                    .into(movieTrailerThumbnail);

            playTrailerButton.setOnClickListener(v -> {
                String url = "https://www.youtube.com/";
                Uri webPage = Uri.parse(url);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, webPage);
                if(browserIntent.resolveActivity(mContext.getPackageManager()) != null){
                    mContext.startActivity(browserIntent);
                }
            });
        }
    }

    public String getMovieTrailerThumbnail(String movieKey){
        return "https://img.youtube.com/vi/" + movieKey + "/0.jpg";
    }
}
