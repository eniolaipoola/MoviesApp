package com.example.moviesapp.models.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.moviesapp.models.MoviesResult;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("Select * from movie order by movieId")
    List<MoviesResult> fetchAllMoviesById();


    @Query("Select * from movie")
    List<MoviesResult> fetchAllMovies();

    @Query("Select * from movie where 'starred' = 'true'")
    List<MoviesResult> getAllStarredMovies();

    @Insert
    void saveMovie(MoviesResult moviesResult);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MoviesResult moviesResult);

    @Delete
    void deleteMovie(MoviesResult moviesResult);
}
