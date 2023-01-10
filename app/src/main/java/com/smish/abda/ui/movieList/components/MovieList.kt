package com.smish.abda.ui.movieList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.smish.abda.data.model.movie.Search

@Composable
fun MovieList(
    movies: LazyPagingItems<Search>,
    savedList: List<String>,
    onMovieClick: (Search) -> Unit,
    onBookmarkClick: (movie: Search) -> Unit
) {
    when (movies.loadState.refresh) {
        LoadState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        is LoadState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Something went wrong")
            }
        }
        else -> {
            /*LazyColumn(modifier = modifier) {
                itemsIndexed(books) { index, item ->
                    item?.let {
                        BookItem(
                            book = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(getBackgroundForIndex(index))
                                .padding(vertical = 15.dp)
                        )
                    }
                }
            }*/
            /*LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxSize()
            ) {
                items(movies) { movie ->
                    if (movie != null) {
//                        val (isChecked, setChecked) = remember { mutableStateOf(false) }
                        if (savedList.contains(movie.imdbID)) movie.isBookmarked = true
                        MovieListItem(
                            movie = movie,
                            onMovieClick = {
                                // call the movie detail api and trigger the bottom sheet
                                onMovieClick(movie)
                            },
                            onBookmarkClick = {
//                                setChecked(!isChecked)
                                movie.toggle()
                                onBookmarkClick(movie)
                            }
                        )
                    }
                }
            }*/

            LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = 135.dp)
                ) {
                items(movies.itemCount) { index ->
                    movies[index].let { movie ->
                        if (movie != null) {
//                        val (isChecked, setChecked) = remember { mutableStateOf(false) }
                            if (savedList.contains(movie.imdbID)) movie.isBookmarked = true
                            MovieListItem(
                                movie = movie,
                                onMovieClick = {
                                    // call the movie detail api and trigger the bottom sheet
                                    onMovieClick(movie)
                                },
                                onBookmarkClick = {
//                                setChecked(!isChecked)
                                    movie.toggle()
                                    onBookmarkClick(movie)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}