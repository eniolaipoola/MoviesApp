package com.example.moviesapp.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public Retrofit getRetrofit(String baseUrl) {

        if(retrofit == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    private OkHttpClient okHttpClient(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        return okHttpClient.build();

    }

}