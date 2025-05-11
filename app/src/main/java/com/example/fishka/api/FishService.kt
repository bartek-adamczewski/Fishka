package com.example.fishka.api

import com.example.fishka.data.FishResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface FishService {
    @Headers("Accept: application/json")
    @GET("v1/taxa")
    suspend fun getAllSpecies(
        @Query("taxon_id") taxonId: Int = 47178,
        @Query("rank") rank: String = "species",
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1,
        @Query("q") query: String?,
    ): FishResponse

    @GET("v1/taxa/{id}")
    suspend fun getSpeciesById(
        @Path("id") id: Int
    ): FishResponse

    @GET("v1/taxa/autocomplete")
    suspend fun autocompleteSpecies(
        @Query("q") query: String,
        @Query("taxon_id") taxonId: Int = 47178,
        @Query("rank") rank: String = "species",
        @Query("preferred_place_id") placeId: Int = 1
    ): FishResponse
}