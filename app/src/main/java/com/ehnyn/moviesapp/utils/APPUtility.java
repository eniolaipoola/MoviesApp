package com.ehnyn.moviesapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class APPUtility {


    public boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return  networkInfo != null && networkInfo.isConnected();
    }

    public String buildMoviePosterUrl(String posterRelativePath){
        if(posterRelativePath != null && !posterRelativePath.equals("")){
            return APPConstant.MOVIE_POSTER_BASE_URL + APPConstant.MOVIE_POSTER_SIZE + posterRelativePath;

        } else {
            return null;
        }
    }
}
