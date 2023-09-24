package com.ehnyn.moviesapp.models.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ehnyn.moviesapp.models.MoviesResult;

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
