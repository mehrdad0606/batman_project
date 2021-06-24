package com.iranian.cards.android.batmanproject.data.api.retrofit.model

import com.google.gson.annotations.SerializedName

data class Ratings(
    @SerializedName("Source")
    var Source: String = "",
    @SerializedName("Value")
    var Value: String = ""
)