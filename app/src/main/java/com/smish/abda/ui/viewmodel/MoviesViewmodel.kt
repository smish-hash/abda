package com.smish.abda.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.movie.Search
import com.smish.abda.data.model.movie.Type
import com.smish.abda.domain.usecase.*
import com.smish.abda.ui.movieList.MovieDetailState
import com.smish.abda.ui.movieList.MovieListState
import com.smish.abda.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _movieState = mutableStateOf(MovieListState())
    val movieState: State<MovieListState> = _movieState

    private var topMovies: List<Search?>? = emptyList()

    private val _movieDetailState = mutableStateOf(MovieDetailState())
    val movieDetailState: State<MovieDetailState> = _movieDetailState

    init {
        getMovies("why", 1)
    }

    fun getMovies(searchQuery: String, page: Int) = viewModelScope.launch { Dispatchers.IO
//        movies.postValue(Resource.Loading())
        _movieState.value = MovieListState(isLoading = true)
        try {
            if (isNetworkAvailable(app)) {
                    val result = getMovies.getMovies(searchQuery, page)
                    Log.d("movies", "getMovies: ${result.data}")
                    when (result) {
                        is Resource.Error -> _movieState.value =
                            MovieListState(error = result.message ?: "Unexpected Error occurred")
                        is Resource.Loading -> _movieState.value = MovieListState(isLoading = true)
                        is Resource.Success -> {
                            _movieState.value = MovieListState(movies = result.data)
                            topMovies = _movieState.value.movies?.movies
                            /*if (page == 5)
                                _movieState.value = MovieListState(
                                    movies = Movie(
                                        "True",
                                        topMovies,
                                        topMovies.size.toString()
                                    )
                                )
                            else {
                                result.data?.movies?.forEach {
                                    if (it != null) {
                                        topMovies.add(it)
                                    }
                                }
                            }*/
                        }
                    }
//                movies.postValue(result)
            } else {
//                movies.postValue(Resource.Error("Internet is not available"))
                _movieState.value = MovieListState(error = "Internet is not available")
            }
        } catch (e: Exception) {
//            movies.postValue(Resource.Error(e.message ?: "An unknown error occurred"))
            _movieState.value = MovieListState(error = e.message ?: "An unknown error occurred")
        }
    }

    fun getMovieDetails(imdbId: String) = viewModelScope.launch { Dispatchers.IO
        _movieDetailState.value = MovieDetailState(isLoading = true)
        try {
            if (isNetworkAvailable(app)) {
                val result = getMovieDetail.getMovieDetails(imdbId)
                Log.d("movies", "getMovieDetails: ${result.data}")
                when (result) {
                    is Resource.Error -> _movieDetailState.value = MovieDetailState(error = result.message ?: "Unexpected Error occurred")
                    is Resource.Loading -> _movieDetailState.value = MovieDetailState(isLoading = true)
                    is Resource.Success -> {
                        _movieDetailState.value = MovieDetailState(movies = result.data)
                    }
                }
            } else {
                _movieState.value = MovieListState(error = "Internet is not available")
            }
        } catch (e: Exception) {
            _movieState.value = MovieListState(error = e.message ?: "An unknown error occurred")
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

    fun performQuery(query: String, page: Int) {
        getMovies(query, page)
    }

    fun getSpecificType(type: String) {
        when (type) {
            Type.HOME.value -> {
                if (topMovies.isNullOrEmpty())
                    _movieState.value = MovieListState(error = "Unknown error occurred, please try again")
                else
                    _movieState.value = MovieListState(movies = Movie("True", topMovies, topMovies?.size.toString()))
            }

            Type.MOVIE.value -> {
                var movieList = topMovies
                movieList = movieList?.filter {
                    it?.type == Type.MOVIE.value
                }
                if (movieList.isNullOrEmpty())
                    _movieState.value = MovieListState(error = "Match not found")
                else
                    _movieState.value = MovieListState(movies = Movie("True", movieList, movieList.size.toString()))
            }

            Type.SERIES.value -> {
                var movieList = topMovies
                movieList = movieList?.filter {
                    it?.type == Type.SERIES.value
                }
                if (movieList.isNullOrEmpty())
                    _movieState.value = MovieListState(error = "Match not found")
                else
                    _movieState.value = MovieListState(movies = Movie("True", movieList, movieList.size.toString()))
            }

            Type.EPISODE.value -> {
                var movieList = topMovies
                movieList = movieList?.filter {
                    it?.type == Type.EPISODE.value
                }
                if (movieList.isNullOrEmpty())
                    _movieState.value = MovieListState(error = "Match not found")
                else
                    _movieState.value = MovieListState(movies = Movie("True", movieList, movieList.size.toString()))
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