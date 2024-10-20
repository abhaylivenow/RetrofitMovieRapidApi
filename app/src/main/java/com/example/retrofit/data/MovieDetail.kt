package com.example.retrofit.data

data class MovieDetail(
    val data: MovieData
)

data class MovieData(
    val title: Title
)

data class Title(
    val id: String,
    val primaryImage: PrimaryImage,
    val titleText: TitleText
)

data class TitleText(
    val isOriginalTitle: Boolean,
    val text: String
)

data class PrimaryImage(
    val url: String
)