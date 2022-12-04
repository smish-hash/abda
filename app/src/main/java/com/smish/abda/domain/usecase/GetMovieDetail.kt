package com.smish.abda.domain.usecase

import com.smish.abda.data.model.moviedetail.MovieDetail
import com.smish.abda.domain.repository.MovieRepository
import com.smish.abda.util.Resource

class GetMovieDetail(private val movieRepository: MovieRepository) {
    suspend fun getMovieDetails(imdbId: String):Resource<MovieDetail> {
        return movieRepository.getMovieDetails(imdbId)
    }
}