package com.example.fishka.data

import com.google.gson.annotations.SerializedName

data class FishPhoto(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("square_url") val squareUrl: String,
    @SerializedName("medium_url") val mediumUrl: String,
    @SerializedName("original_dimensions") val dimensions: PhotoDimensions
)
