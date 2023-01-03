package com.smish.abda.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.movie.Search
import com.smish.abda.data.model.movie.Type
import com.smish.abda.domain.usecase.*
import com.smish.abda.ui.movieList.MovieDetailState
import com.smish.abda.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewmodel @Inject constructor(
    private val app: Application,
    private val getMovies: GetMovies,
    private val getMovieDetail: GetMovieDetail,
    private val bookmarkMovie: BookmarkMovie,
    private val getBookmarkedMovies: GetBookmarkedMovies,
    private val removeMovie: RemoveMovie
): AndroidViewModel(app) {

    val movies: MutableLiveData<Resource<Movie>> = MutableLiveData()

    private val _movieDetailState = mutableStateOf(MovieDetailState())
    val movieDetailState: State<MovieDetailState> = _movieDetailState

    private var query = mutableStateOf("")
    private var _type = mutableStateOf("")

    fun getPagingMovies(): Flow<PagingData<Search>> {
        val pager = getMovies.getMovies(query.value, _type.value, 1)
        pager.cachedIn(viewModelScope)
        return pager
    }

    fun setQuery(query: String) {
        this.query.value = query
    }


    fun getMovieDetails(imdbId: String) = viewModelScope.launch { Dispatchers.IO
        _movieDetailState.value = MovieDetailState(isLoading = true)
        try {
            if (isNetworkAvailable(app)) {
                when (val result = getMovieDetail.getMovieDetails(imdbId)) {
                    is Resource.Error -> _movieDetailState.value = MovieDetailState(error = result.message ?: "Unexpected Error occurred")
                    is Resource.Loading -> _movieDetailState.value = MovieDetailState(isLoading = true)
                    is Resource.Success -> {
                        _movieDetailState.value = MovieDetailState(movies = result.data)
                    }
                }
            } else {
                _movieDetailState.value = MovieDetailState(error = "Internet is not available")
            }
        } catch (e: Exception) {
            _movieDetailState.value = MovieDetailState(error = e.message ?: "An unknown error occurred")
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        var result = false
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return result
    }

    fun setSpecificType(type: String) {
        when (type) {
            Type.HOME.value -> {
                _type.value = ""
            }

            Type.MOVIE.value -> {
                _type.value = Type.MOVIE.value
            }

            Type.SERIES.value -> {
                _type.value = Type.SERIES.value
            }
            Type.EPISODE.value -> {
                _type.value = Type.EPISODE.value
            }
        }
    }

    // local storage
    fun bookmarkMovie(movie: Search) = viewModelScope.launch {
        bookmarkMovie.bookmarkMovie(movie)
    }

    fun removeMovie(movie: Search) = viewModelScope.launch {
        removeMovie.removeMovie(movie)
    }

    fun getBookmarkedMovies() = liveData {
        getBookmarkedMovies.getBookmarkedMovies().collect {
            emit(it)
        }
    }
}