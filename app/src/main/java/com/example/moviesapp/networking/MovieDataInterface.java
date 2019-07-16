package com.example.moviesapp.networking;

import com.example.moviesapp.models.MoviesResult;

import java.util.List;

public interface MovieDataInterface {

    void onSuccess(List<MoviesResult> moviesResult);

    void onFailedResponse(String errorMessage);
}
