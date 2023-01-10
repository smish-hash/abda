package com.smish.abda.ui.movieList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.smish.abda.data.model.movie.Search

@Composable
fun MovieList(
    movies: LazyPagingItems<Search>,
    savedList: List<String>,
    onMovieClick: (Search) -> Unit,
    onBookmarkClick: (isChecked: Boolean, movie: Search) -> Unit
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
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxSize()
            ) {
                items(movies) { movie ->
                    if (movie != null) {
                        val (isChecked, setChecked) = remember { mutableStateOf(false) }
                        if (savedList.contains(movie.imdbID)) setChecked(true) else setChecked(false)
                        MovieListItem(
                            movie = movie,
                            onMovieClick = {
                                // call the movie detail api and trigger the bottom sheet
                                onMovieClick(movie)
                            },
                            isChecked,
                            onBookmarkClick = {
                                setChecked(!isChecked)
                                onBookmarkClick(isChecked, movie)
                            }
                        )
                    }
                }
            }
        }
    }
}