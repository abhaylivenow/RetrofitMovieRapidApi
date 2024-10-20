package com.example.retrofit.data.genre

data class GenreResponse(
    val data: Data
)

data class Data(
    val title: Title
)

data class Title(
    val id: String,
    val titleGenres: TitleGenres,
    val titleType: TitleType
)

data class TitleGenres(
    val __typename: String,
    val genres: List<Genre>
)

data class Genre(
    val genre: GenreX
)

data class GenreX(
    val genreId: String,
    val text: String
)