package com.ehnyn.moviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ehnyn.moviesapp.models.Database.AppDatabase;
import com.ehnyn.moviesapp.models.MoviesResult;
import com.ehnyn.moviesapp.utils.APPConstant;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<MoviesResult>> moviesResult;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        Log.d(APPConstant.DEBUG_TAG, "View model is being used");
        moviesResult = appDatabase.movieDao().fetchAllMovies();
    }

    public LiveData<List<MoviesResult>> getMoviesResult() {
        return moviesResult;
    }

    public void setMoviesResult(LiveData<List<MoviesResult>> moviesResult) {
        this.moviesResult = moviesResult;
    }
}
