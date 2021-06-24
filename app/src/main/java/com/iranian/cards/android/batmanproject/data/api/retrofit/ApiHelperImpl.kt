package com.iranian.cards.android.batmanproject.data.api.retrofit

import com.iranian.cards.android.batmanproject.data.api.ApiHelper
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MovieDetailsResponse
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MoviesResponse

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getMoviesResponse(s:String): MoviesResponse {
        return apiService.getMoviesResponses(s)
    }

    override suspend fun getMovieDetailResponse(imdbId: String): MovieDetailsResponse {
        return apiService.getMovieDetailResponse(imdbId)
    }
}