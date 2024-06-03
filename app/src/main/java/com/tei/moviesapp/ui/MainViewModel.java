package com.tei.moviesapp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.tei.moviesapp.models.Database.AppDatabase;
import com.tei.moviesapp.models.MoviesResult;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<MoviesResult>> moviesResult;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        moviesResult = appDatabase.movieDao().fetchAllMovies();
    }

    public LiveData<List<MoviesResult>> getMoviesResult() {
        return moviesResult;
    }

    public void setMoviesResult(LiveData<List<MoviesResult>> moviesResult) {
        this.moviesResult = moviesResult;
    }
}
