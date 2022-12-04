package com.smish.abda.data.repository.datasourceimpl

import com.smish.abda.data.api.MovieApiService
import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.moviedetail.MovieDetail
import com.smish.abda.data.repository.datasource.MovieRemoteDataSource
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val movieAPI: MovieApiService
): MovieRemoteDataSource {

    override suspend fun getMovies(searchQuery: String, page: Int): Response<Movie> {
        return movieAPI.getMovies(searchQuery, page)
    }

    override suspend fun getMovieDetails(imdbId: String): Response<MovieDetail> {
        return movieAPI.getMovieDetails(imdbId)
    }
}