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

    val homeLoadingLD = MutableStateFlow<Boolean>(false)
    val searchLoadingLD = MutableStateFlow<Boolean>(false)

    init {
        getPopularMovies()
        //getSearchedMovie("iron")
    }

    private fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // loader is visible
                homeLoadingLD.value = true
                Log.i("PostViewModel47945798","network call begin")
                val getMovieIdList = repository.getPopularMoviesId()
                getMovieById(getMovieIdList)
                Log.i("PostViewModel Success","no error occurred")
                    // loader is gone
            } catch (e: Exception) {
                // loader is gone
                homeLoadingLD.value = false
                Log.i("PostViewModel Error","some error occurred ${e.message}")
            }

        }
    }

    private fun getMovieById(ids: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieFromId = repository.getMovieById(ids)
            movieFromIdFlow.value = movieFromId
            homeLoadingLD.value = false
        }
    }

    fun getSearchedMovie(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                searchLoadingLD.value = true
                val result = repository.getSearchedMovieList(query)
                searchedMovieList.value = result
                searchLoadingLD.value = false
            } catch (e: Exception) {
                searchLoadingLD.value = false
            }
        }
    }

    fun getMovieGenre(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getMovieGenreResponse(id)
            movieGenreFlow.value = result
        }
    }
}













