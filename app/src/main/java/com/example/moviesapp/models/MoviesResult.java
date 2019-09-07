package com.example.moviesapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "movie")
public class MoviesResult {

    @PrimaryKey(autoGenerate = true)
    private int movieId;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("vote_average")
    @Expose
    private double rating;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("overview")
    @Expose
    private String plotSynopsis;

    private int starred;

    private Date updatedAt;

    private Date createdAt;

    private String moviePosterUrl;

    @Ignore
    public MoviesResult(int id, String releaseDate, double rating, String posterPath,
                         String originalTitle, String plotSynopsis, String moviePosterUrl, int starred, Date createdAt, Date updatedAt) {
        this.id = id;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.moviePosterUrl = moviePosterUrl;
        this.starred = starred;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public MoviesResult(int id, int movieId, String releaseDate, double rating, String posterPath,
                        String originalTitle, String plotSynopsis, String moviePosterUrl, int starred, Date createdAt, Date updatedAt) {
        this.movieId = movieId;
        this.id = id;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.moviePosterUrl = moviePosterUrl;
        this.starred = starred;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }

    public int getStarred() {
        return starred;
    }

    public void setStarred(int starred) {
        this.starred = starred;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
