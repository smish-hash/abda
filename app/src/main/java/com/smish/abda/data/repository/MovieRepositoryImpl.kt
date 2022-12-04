package com.smish.abda.data.repository

import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.movie.Search
import com.smish.abda.data.model.moviedetail.MovieDetail
import com.smish.abda.data.repository.datasource.MovieLocalDataSource
import com.smish.abda.data.repository.datasource.MovieRemoteDataSource
import com.smish.abda.domain.repository.MovieRepository
import com.smish.abda.util.Resource
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
): MovieRepository {

    override suspend fun getMovies(searchQuery: String, page: Int): Resource<Movie> {
        val response = movieRemoteDataSource.getMovies(searchQuery, page)
        if (response.isSuccessful) {
            response.body()?.let { res ->
                if (res.response == "True") {
                    return Resource.Success(res)
                } else {
                    return Resource.Error(res.error ?: "Unknown error occurred")
                }
            }
        }
        return Resource.Error(response.message() ?: "Unknown error occurred")
    }

    override suspend fun getMovieDetails(imdbId: String): Resource<MovieDetail> {
        val response = movieRemoteDataSource.getMovieDetails(imdbId)
        if (response.isSuccessful) {
            response.body()?.let { res ->
                if (res.response == "True") {
                    return Resource.Success(res)
                } else {
                    return Resource.Error(res.error ?: "Unknown error occurred")
                }
            }
        }
        return Resource.Error(response.message() ?: "Unknown error occurred")
    }

    override suspend fun bookmarkMovie(movie: Search) {
        movieLocalDataSource.saveMovieToDB(movie)
    }

    override suspend fun removeMovie(movie: Search) {
        movieLocalDataSource.deleteMovieFromDB(movie.imdbID)
    }

    override fun getBookmarkedMovies(): Flow<List<Search>> {
        return movieLocalDataSource.getSavedMoviesFromDB()
    }
}