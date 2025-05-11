package com.example.fishka.data

import com.google.gson.annotations.SerializedName

data class Fish(
    @SerializedName("id") val id: Int,
    @SerializedName("rank") val rank: String,
    @SerializedName("rank_level") val rankLevel: Int,
    @SerializedName("iconic_taxon_id") val iconicTaxonId: Int,
    @SerializedName("iconic_taxon_name") val iconicTaxonName: String?,
    @SerializedName("preferred_common_name") val commonName: String?,
    @SerializedName("name") val scientificName: String,
    @SerializedName("observations_count") val observationsCount: Int?,
    @SerializedName("colors") val colors: List<FishColor>?,
    @SerializedName("conservation_status") val conservationStatus: ConservationStatus?,
    @SerializedName("conservation_statuses") val conservationStatuses: List<ConservationStatus>?,
    @SerializedName("establishment_means") val establishmentMeans: EstablishmentMeans?,
    @SerializedName("preferred_establishment_means") val preferredEstablishmentMeans: String?,
    @SerializedName("extinct") val extinct: Boolean?,
    @SerializedName("wikipedia_url") val wikipediaUrl: String?,
    @SerializedName("default_photo") val defaultPhoto: FishPhoto?,
    @SerializedName("ancestor_ids") val ancestorIds: List<Int>?,
    @SerializedName("taxon_photos") val taxonPhotos: List<FishPhoto>?,
    @SerializedName("complete_rank") val completeRank: String?,
    @SerializedName("exact_match") val exactMatch: Boolean?
)

