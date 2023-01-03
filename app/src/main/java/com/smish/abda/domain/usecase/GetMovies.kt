package com.smish.abda.domain.usecase

import androidx.paging.PagingData
import com.smish.abda.data.model.movie.Search
import com.smish.abda.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMovies(private val movieRepository: MovieRepository) {
    fun getMovies(searchQuery: String, type: String, page: Int): Flow<PagingData<Search>> {
        return movieRepository.getMovies(searchQuery, type, page)
    }
}