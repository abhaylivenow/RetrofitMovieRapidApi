package com.example.retrofit

import android.util.Log
import com.example.retrofit.data.MovieDetail
import com.example.retrofit.data.SearchedData.SearchedDataResponse
import com.example.retrofit.data.genre.GenreResponse
import com.example.retrofit.retrofit.RetrofitBuilder

class Repository {

    private val apiServices = RetrofitBuilder.getApi

    suspend fun getPopularMoviesId(): List<String> = apiServices.getPopularMoviesId(
        key = "a32882bd46msh090023205a198bap19e7e6jsn89926a0de99b",
        host = "imdb8.p.rapidapi.com"
    )

    suspend fun getSearchedMovieList(query: String): SearchedDataResponse? = apiServices.getSearchedMovie(
        key = "a32882bd46msh090023205a198bap19e7e6jsn89926a0de99b",
        host = "imdb8.p.rapidapi.com",
        query
    )

    suspend fun getMovieGenreResponse(id: String): GenreResponse? = apiServices.getGenres(
        key = "a32882bd46msh090023205a198bap19e7e6jsn89926a0de99b",
        host = "imdb8.p.rapidapi.com",
        id
    )

    suspend fun getMovieById(ids: List<String>): List<MovieDetail> {

        Log.i("Repo92329","18")
        val filteredList = ids.take(10).map {
            cleanTitleId(it)
        }
        Log.i("Repo92329","22 "+filteredList.toString())

        val listOfMovieDetails = mutableListOf<MovieDetail>()

        filteredList.forEach { id ->
            val apiRes = apiServices.getMovieById(
                key = "a32882bd46msh090023205a198bap19e7e6jsn89926a0de99b",
                host = "imdb8.p.rapidapi.com",
                id
            )
            apiRes?.let {
                listOfMovieDetails.add(it)
            }
        }
        Log.i("Repo92329","36 ")

        return listOfMovieDetails
    }

    private fun cleanTitleId(input: String): String {
        return input.substringAfter("/title/").substringBefore("/")
    }
}