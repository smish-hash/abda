package com.smish.abda.domain.usecase

import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.movie.Search
import com.smish.abda.domain.repository.MovieRepository

class BookmarkMovie(private val movieRepository: MovieRepository) {
    suspend fun bookmarkMovie(movie: Search) = movieRepository.bookmarkMovie(movie)
}