package com.ehnyn.moviesapp.models;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.ehnyn.moviesapp.models.Database.AppDatabase;
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
