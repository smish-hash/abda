package com.smish.abda.ui.movieBookmark.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smish.abda.R
import com.smish.abda.data.model.movie.Type
import com.smish.abda.data.model.movie.getAllTypes
import com.smish.abda.data.model.movie.getType
import com.smish.abda.data.model.moviedetail.MovieDetail
import com.smish.abda.ui.movieList.components.MovieDetailBody
import com.smish.abda.ui.movieList.components.MovieDetailHeader
import com.smish.abda.ui.viewmodel.MoviesViewmodel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieBookmarkScreen(
    viewmodel: MoviesViewmodel = hiltViewModel()
) {
    val movieDetailState = viewmodel.movieDetailState.value

    val scope = rememberCoroutineScope()
    val bottomSheetModalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val showModalSheet = rememberSaveable {
        mutableStateOf(false)
    }

    val bookmarkList by viewmodel.getBookmarkedMovies().observeAsState()

    ModalBottomSheetLayout(
        sheetState = bottomSheetModalState,
        sheetContent = {
            var movie: MovieDetail? = null
            if (movieDetailState.movies != null) {
                movie = movieDetailState.movies
                LaunchedEffect(bottomSheetModalState) {
                    bottomSheetModalState.show()
                }
            }
            Column(
                modifier = Modifier.background(
                    colorResource(id = R.color.BackgroundYellow)
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MovieDetailHeader(
                    movie = movie,
                    onDismissClick = {
                        scope.launch {
                            if (bottomSheetModalState.isVisible)
                                bottomSheetModalState.hide()
                        }
                    }
                )

                if (movie != null)
                    MovieDetailBody(movie = movie)

                if (movieDetailState.error.isNotBlank()) {
                    Text("Error: ${movieDetailState.error}")
                }

                if (movieDetailState.isLoading) {
                    LaunchedEffect(bottomSheetModalState) {
                        if (!bottomSheetModalState.isVisible) {
                            bottomSheetModalState.show()
                        }
                    }
                    CircularProgressIndicator()
                }
            }
        },
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(16.dp),
    ) {
        // main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = 135.dp)
                ) {
                    items(bookmarkList ?: emptyList()) { movie ->
                        if (movie != null) {
                            MovieBookmarkItem(
                                movie = movie,
                                onMovieClick = {
                                    // call the movie detail api and trigger the bottom sheet
                                    showModalSheet.value = !showModalSheet.value
                                    viewmodel.getMovieDetails(movie.imdbID)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}