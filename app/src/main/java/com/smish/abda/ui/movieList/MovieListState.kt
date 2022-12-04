package com.smish.abda.ui.movieList

import com.smish.abda.data.model.movie.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: Movie ?= null,
    val error: String = ""
)
