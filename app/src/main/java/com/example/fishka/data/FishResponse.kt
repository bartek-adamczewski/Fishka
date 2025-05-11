package com.example.fishka.data

import com.google.gson.annotations.SerializedName

data class FishResponse(
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("results") val results: List<Fish>
)
