package com.smish.abda.data.repository.datasource

import androidx.paging.PagingData
import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.movie.Search
import com.smish.abda.data.model.moviedetail.MovieDetail
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRemoteDataSource {
    // defining functions to communicate with the API
    // use for both search and all
    fun getMovies(searchQuery: String, page: Int): Flow<PagingData<Search>>
    suspend fun getMovieDetails(imdbId: String): Response<MovieDetail>
}