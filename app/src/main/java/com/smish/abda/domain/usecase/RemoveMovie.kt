package com.smish.abda.domain.usecase

import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.movie.Search
import com.smish.abda.domain.repository.MovieRepository

class RemoveMovie(private val movieRepository: MovieRepository) {
    suspend fun removeMovie(movie: Search) = movieRepository.removeMovie(movie)
}