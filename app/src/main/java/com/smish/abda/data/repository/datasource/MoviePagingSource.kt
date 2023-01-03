package com.smish.abda.data.repository.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smish.abda.data.api.MovieApiService
import com.smish.abda.data.model.movie.Search
import okio.IOException
import retrofit2.HttpException

private const val OMDB_STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val query: String,
    private val movieApiService: MovieApiService
): PagingSource<Int, Search>() {

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        /* We need to get the previous key (or next key if previous is null) of the page
         that was closest to the most recently accessed index.
         Anchor position is the most recently accessed index.*/
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        val pageIndex = params.key ?: OMDB_STARTING_PAGE_INDEX

        return try {
            val response = movieApiService.getMovies(
                searchQuery = query,
                page = pageIndex
            )
            // can return load state . error here

            val movies: List<Search> = (response.movies ?: emptyList()) as List<Search>
            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {
                    /* By default, initial load size = 3 * NETWORK PAGE SIZE
                     ensure we're not requesting duplicating items at the 2nd request
                     pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)*/
                    pageIndex + 1
                }
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == OMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}