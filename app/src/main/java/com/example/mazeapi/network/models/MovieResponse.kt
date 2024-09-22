package com.example.mazeapi.network.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<MovieResult> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null

)