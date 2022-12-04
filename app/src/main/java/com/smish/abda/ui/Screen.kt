package com.smish.abda.ui

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object MovieListScreen: Screen("movie_list_screen")
    object MovieDetailScreen: Screen("movie_detail_screen")
    object MovieBookmarkScreen: Screen("movie_bookmark_screen")
}
