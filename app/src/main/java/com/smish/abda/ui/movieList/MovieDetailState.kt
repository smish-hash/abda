package com.smish.abda.ui.movieList

import com.smish.abda.data.model.moviedetail.MovieDetail

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movies: MovieDetail ?= null,
    val error: String = ""
)
