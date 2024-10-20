package com.example.retrofit.data.SearchedData

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val i: I,
    val id: String,
    @SerializedName("l")
    val movieName: String,
    val q: String,
    val qid: String,
    val rank: Int,
    val s: String,
    val y: Int,
    val yr: String
)