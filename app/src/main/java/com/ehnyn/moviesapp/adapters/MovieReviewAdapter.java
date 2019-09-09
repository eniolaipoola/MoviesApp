package com.ehnyn.moviesapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ehnyn.moviesapp.R;
import com.ehnyn.moviesapp.models.MovieReviewModel;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewAdapterViewHolder> {

    private List<MovieReviewModel.MovieReviewResult> movieReviewResults;

    public MovieReviewAdapter(List<MovieReviewModel.MovieReviewResult> reviewResults) {
        this.movieReviewResults = reviewResults ;
    }

    @NonNull
    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view =  layoutInflater.inflate(R.layout.movie_review, viewGroup, false);
        return new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewAdapterViewHolder movieTrailerAdapterViewHolder, int position) {
        final MovieReviewModel.MovieReviewResult movieReviewResult = movieReviewResults.get(position);
        movieTrailerAdapterViewHolder.bindResultToView(movieReviewResult);
    }

    @Override
    public int getItemCount() {
        return movieReviewResults.size();
    }

    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView reviewAuthor;
        TextView reviewContent;
        TextView reviewUrl;

        MovieReviewAdapterViewHolder(final View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.reviewAuthor);
            reviewContent = itemView.findViewById(R.id.reviewContent);
            reviewUrl = itemView.findViewById(R.id.reviewUrl);
        }

        private void bindResultToView(final MovieReviewModel.MovieReviewResult reviewResult){
            String author = reviewResult.getAuthor();
            reviewAuthor.setText(author);
            String url = reviewResult.getUrl();
            reviewUrl.setText(url);
            String content = reviewResult.getContent();
            reviewContent.setText(content);
        }
    }
}
