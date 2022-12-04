package com.smish.abda.data.repository.datasource

import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.moviedetail.MovieDetail
import retrofit2.Response

interface MovieRemoteDataSource {
    // defining functions to communicate with the API
    // use for both search and all
    suspend fun getMovies(searchQuery: String, page: Int): Response<Movie>
    suspend fun getMovieDetails(imdbId: String): Response<MovieDetail>
}