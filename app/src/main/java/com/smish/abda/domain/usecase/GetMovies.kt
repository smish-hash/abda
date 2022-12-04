package com.smish.abda.domain.usecase

import com.smish.abda.data.model.movie.Movie
import com.smish.abda.domain.repository.MovieRepository
import com.smish.abda.util.Resource

class GetMovies(private val movieRepository: MovieRepository) {
    suspend fun getMovies(searchQuery: String, page: Int): Resource<Movie> {
        return movieRepository.getMovies(searchQuery, page)
    }
}