package com.smish.abda.data.repository.datasourceimpl

import com.smish.abda.data.db.MovieDao
import com.smish.abda.data.model.movie.Search
import com.smish.abda.data.repository.datasource.MovieLocalDataSource
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao
): MovieLocalDataSource {
    override suspend fun saveMovieToDB(movie: Search) {
        movieDao.insert(movie)
    }

    override fun getSavedMoviesFromDB(): Flow<List<Search>> {
        return movieDao.getAllSavedMovies()
    }

    override suspend fun deleteMovieFromDB(imdbID: String) {
        return movieDao.delete(imdbID)
    }
}