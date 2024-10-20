package com.example.retrofit.retrofit

import com.example.retrofit.data.MovieDetail
import com.example.retrofit.data.SearchedData.SearchedDataResponse
import com.example.retrofit.data.genre.GenreResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiServices {

    @GET("title/get-most-popular-movies")
    suspend fun getPopularMoviesId(
        @Header("x-rapidapi-key") key: String,
        @Header("x-rapidapi-host") host: String,
    ): List<String>

    @GET("/title/v2/get-details")
    suspend fun getMovieById(
        @Header("x-rapidapi-key") key: String,
        @Header("x-rapidapi-host") host: String,
        @Query("tconst") id: String
    ): MovieDetail?

    @GET("/auto-complete")
    suspend fun getSearchedMovie(
        @Header("x-rapidapi-key") key: String,
        @Header("x-rapidapi-host") host: String,
        @Query("q") query: String
    ): SearchedDataResponse?

    @GET("/title/v2/get-genres")
    suspend fun getGenres(
        @Header("x-rapidapi-key") key: String,
        @Header("x-rapidapi-host") host: String,
        @Query("tconst") id: String
    ): GenreResponse?
}