package com.example.moviesapp.models.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("Select * from movie order by id")
    List<StarredMovies> fetchAllMovies();

    @Query("Select * from movie where 'starred' = 'true'")
    List<StarredMovies> getAllStarredMovies();

    @Insert
    void saveMovie(StarredMovies starredMovies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(StarredMovies starredMovies);

    @Delete
    void deleteMovie(StarredMovies starredMovies);
}
