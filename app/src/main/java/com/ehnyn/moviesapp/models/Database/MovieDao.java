package com.ehnyn.moviesapp.models.Database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ehnyn.moviesapp.models.MoviesResult;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("Select * from movie order by movieId")
    List<MoviesResult> fetchAllMoviesById();


    @Query("Select * from movie")
    LiveData<List<MoviesResult>> fetchAllMovies();

    @Query("Select * from movie where 'starred' = 'true'")
    List<MoviesResult> getAllStarredMovies();

    @Insert
    void saveMovie(MoviesResult moviesResult);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MoviesResult moviesResult);

    @Delete
    void deleteMovie(MoviesResult moviesResult);
}
