package com.example.fishka.data

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("display_name") val displayName: String
)
