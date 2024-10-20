package com.example.retrofit

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.data.MovieDetail
import com.example.retrofit.data.SearchedData.SearchedDataResponse
import com.example.retrofit.data.genre.GenreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PostsViewModel(
    private val repository: Repository
) : ViewModel() {

    val searchedMovieList = MutableStateFlow<SearchedDataResponse?>(null)
    val movieFromIdFlow = MutableStateFlow<List<MovieDetail>>(emptyList())
    val movieGenreFlow = MutableStateFlow<GenreResponse?>(null)

    init {
        getPopularMovies()
        //getSearchedMovie("iron")
    }

    private fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i("PostViewModel47945798","network call begin")
                val getMovieIdList = repository.getPopularMoviesId()
                getMovieById(getMovieIdList)
                Log.i("PostViewModel Success","no error occurred")
            } catch (e: Exception) {
                Log.i("PostViewModel Error","some error occurred")
            }

        }
    }

    private fun getMovieById(ids: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieFromId = repository.getMovieById(ids)
            movieFromIdFlow.value = movieFromId
        }
    }

    fun getSearchedMovie(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getSearchedMovieList(query)
            searchedMovieList.value = result
        }
    }

    fun getMovieGenre(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getMovieGenreResponse(id)
            movieGenreFlow.value = result
        }
    }
}













