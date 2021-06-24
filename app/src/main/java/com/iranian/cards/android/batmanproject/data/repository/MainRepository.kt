package com.iranian.cards.android.batmanproject.data.repository

import com.iranian.cards.android.batmanproject.data.api.ApiHelper
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MovieDetailsResponse
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MoviesResponse

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getMoviesResponseFromServer(s:String): MoviesResponse {
        return apiHelper.getMoviesResponse(s)
    }

    suspend fun getMovieDetailResponseFromServer(imdbId:String):MovieDetailsResponse {
        return apiHelper.getMovieDetailResponse(imdbId)
    }
}