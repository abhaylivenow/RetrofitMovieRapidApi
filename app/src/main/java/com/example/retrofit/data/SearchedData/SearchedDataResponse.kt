package com.example.retrofit.data.SearchedData

import com.google.gson.annotations.SerializedName

data class SearchedDataResponse(
    @SerializedName("d")
    val movieDetail: List<MovieDetail>,
    val q: String,
    val v: Int
)