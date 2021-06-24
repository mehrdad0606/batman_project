package com.iranian.cards.android.batmanproject.data.api.retrofit.model

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("Title")
    var Title: String ="",
    @SerializedName("Year")
    var Year: String = "",
    @SerializedName("imdbID")
    var imdbID: String = "",
    @SerializedName("Type")
    var Type: String = "",
    @SerializedName("Poster")
    var Poster: String = ""
)