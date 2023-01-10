package com.smish.abda.domain.usecase

import com.smish.abda.data.model.movie.Search
import com.smish.abda.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetBookmarkedMovies(private val movieRepository: MovieRepository) {
    fun getBookmarkedMovies(): Flow<List<Search>> {
        return movieRepository.getBookmarkedMovies()
    }

    fun getSavedMoviesIds(): Flow<List<String>> {
        return movieRepository.getSavedMoviesIds()
    }
}