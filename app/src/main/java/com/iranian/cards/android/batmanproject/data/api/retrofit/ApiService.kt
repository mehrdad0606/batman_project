package com.iranian.cards.android.batmanproject.data.api.retrofit

import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MovieDetailsResponse
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    //get movies response..................................................................
    @Headers(
        "Accept: application/json; version=1.0",
        "Content-Type: application/json; version=1.0"
    )
    @GET(ApiConstants.apiKey)
    suspend fun getMoviesResponses(@Query("s") s:String): MoviesResponse

    //get movie detail response..................................................................
    @Headers(
        "Accept: application/json; version=1.0",
        "Content-Type: application/json; version=1.0"
    )
    @GET(ApiConstants.apiKey)
    suspend fun getMovieDetailResponse(@Query("i") i:String): MovieDetailsResponse

}