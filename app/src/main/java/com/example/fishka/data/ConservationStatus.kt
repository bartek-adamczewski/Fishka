package com.example.fishka.data

import com.google.gson.annotations.SerializedName

data class ConservationStatus(
    @SerializedName("status") val status: String?,
    @SerializedName("authority") val authority: String? = null,
    @SerializedName("status_name") val statusName: String? = null,
    @SerializedName("place") val place: Place? = null
)