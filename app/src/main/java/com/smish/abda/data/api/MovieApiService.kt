package com.smish.abda.data.api

import com.smish.abda.BuildConfig
import com.smish.abda.data.model.movie.Movie
import com.smish.abda.data.model.moviedetail.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(".")
    suspend fun getMovies(
        @Query("s")
        searchQuery: String,
        @Query("page")
        page: Int,
        @Query("type")
        type: String,
        @Query("apikey")
        apiKey: String = BuildConfig.MOVIE_API_KEY
    ): Movie

    @GET(".")
    suspend fun getMovieDetails(
        @Query("i")
        imdbId: String,
        @Query("apikey")
        apiKey: String = BuildConfig.MOVIE_API_KEY
    ): Response<MovieDetail>
}