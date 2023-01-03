package com.smish.abda.data.repository.datasourceimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smish.abda.data.api.MovieApiService
import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.movie.Search
import com.smish.abda.data.model.moviedetail.MovieDetail
import com.smish.abda.data.repository.datasource.MoviePagingSource
import com.smish.abda.data.repository.datasource.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val movieAPI: MovieApiService
): MovieRemoteDataSource {

    override fun getMovies(searchQuery: String, page: Int): Flow<PagingData<Search>> {
//        return movieAPI.getMovies(searchQuery, page)
        // have the implementation here for paging data source, return pager here
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(query = searchQuery, movieApiService = movieAPI)
            }
        ).flow
    }

    override suspend fun getMovieDetails(imdbId: String): Response<MovieDetail> {
        return movieAPI.getMovieDetails(imdbId)
    }
}