package com.example.moviesapp.models.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "movie")
public class StarredMovies {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int movieId;
    private String release_date;
    private String voteAverage;
    private String posterPath;
    private String originalTitle;
    private int starred;
    private String plotSynopsis;
    private Date updatedAt;
    private Date createdAt;

    @Ignore
    public StarredMovies(int movieId, String release_date, String voteAverage, String posterPath,
                         String originalTitle, String plotSynopsis, int starred, Date createdAt, Date updatedAt) {
        this.movieId = movieId;
        this.release_date = release_date;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.starred = starred;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public StarredMovies(int id, int movieId, String release_date, String voteAverage, String posterPath,
                         String originalTitle, String plotSynopsis, int starred, Date createdAt, Date updatedAt) {
        this.id = id;
        this.movieId = movieId;
        this.release_date = release_date;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.starred = starred;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
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

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
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
}
