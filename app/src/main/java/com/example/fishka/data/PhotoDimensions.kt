package com.example.fishka.data

import com.google.gson.annotations.SerializedName

data class PhotoDimensions(
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int
)
