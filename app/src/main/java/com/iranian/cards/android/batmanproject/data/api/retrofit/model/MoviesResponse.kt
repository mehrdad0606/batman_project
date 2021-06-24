package com.iranian.cards.android.batmanproject.data.api.retrofit.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("Search")
    var Search: ArrayList<Movies> = ArrayList(),
    @SerializedName("totalResults")
    var totalResults: String = "",
    @SerializedName("Response")
    var Response: String = ""
)