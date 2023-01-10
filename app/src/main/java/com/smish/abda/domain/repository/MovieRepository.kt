package com.smish.abda.domain.repository

import androidx.paging.PagingData
import com.smish.abda.data.model.movie.Search
import com.smish.abda.data.model.moviedetail.MovieDetail
import com.smish.abda.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    // this will be used for both get all movies and search
    fun getMovies(searchQuery: String, type: String, page: Int): Flow<PagingData<Search>>
    suspend fun getMovieDetails(imdbId: String): Resource<MovieDetail>

    suspend fun bookmarkMovie(movie: Search)
    suspend fun removeMovie(movie: Search)

    // to get data from db as a flow.
    // asynchronous data stream in the viewmodel, collect it and emit it as live data.
    fun getBookmarkedMovies(): Flow<List<Search>>

    fun getSavedMoviesIds(): Flow<List<String>>
}