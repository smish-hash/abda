package com.smish.abda.data.repository.datasource

import com.smish.abda.data.model.movie.Search
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    /*defining functions to interact with the db*/
    suspend fun saveMovieToDB(movie: Search)
    fun getSavedMoviesFromDB(): Flow<List<Search>>
    suspend fun deleteMovieFromDB(imdbID: String)
}