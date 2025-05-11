package com.example.fishka.data

import com.google.gson.annotations.SerializedName

data class EstablishmentMeans(
    @SerializedName("establishment_means") val establishmentMeans: String?,
    @SerializedName("place") val place: Place
)