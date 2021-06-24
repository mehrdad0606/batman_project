package com.iranian.cards.android.batmanproject.data.api

import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MovieDetailsResponse
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MoviesResponse
import kotlin.collections.ArrayList

interface ApiHelper {
    suspend fun getMoviesResponse(s:String): MoviesResponse
    suspend fun getMovieDetailResponse(imdbId:String): MovieDetailsResponse

}