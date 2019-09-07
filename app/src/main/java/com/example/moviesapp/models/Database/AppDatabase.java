package com.example.moviesapp.models.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.example.moviesapp.models.MoviesResult;

@Database(entities = {MoviesResult.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "myMovie";
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context){

        if(mInstance == null){
            synchronized (LOCK){
                mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                        AppDatabase.DATABASE_NAME).build();
            }
        }
        return mInstance;
    }

    public abstract MovieDao movieDao();

}
