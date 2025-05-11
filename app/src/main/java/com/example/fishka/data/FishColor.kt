package com.example.fishka.data

import com.google.gson.annotations.SerializedName

data class FishColor(
    @SerializedName("id") val id: Int,
    @SerializedName("value") val value: String
)