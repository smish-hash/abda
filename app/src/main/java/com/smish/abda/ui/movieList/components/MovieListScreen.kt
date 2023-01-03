package com.smish.abda.ui.movieList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.smish.abda.R
import com.smish.abda.data.model.movie.Type
import com.smish.abda.data.model.movie.getAllTypes
import com.smish.abda.data.model.movie.getType
import com.smish.abda.data.model.moviedetail.MovieDetail
import com.smish.abda.ui.viewmodel.MoviesViewmodel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieListScreen(
    viewmodel: MoviesViewmodel = hiltViewModel()
) {
    val movieDetailState = viewmodel.movieDetailState.value

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val selectedType: MutableState<Type?> = remember { mutableStateOf(Type.HOME) }

    val scope = rememberCoroutineScope()

    // Declaring a Boolean value to store bottom sheet collapsed state
    val bottomSheetModalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val showModalSheet = rememberSaveable {
        mutableStateOf(false)
    }

    val movieList = viewmodel.getPagingMovies().collectAsLazyPagingItems()
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
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                SearchView(state = textState, viewmodel)

                ChipGroup(
                    types = getAllTypes(),
                    selectedType = selectedType.value,
                    onSelectedChanged = {
                        selectedType.value = getType(it)
                        viewmodel.setSpecificType(it)
                    }
                )


                MovieList(
                    movies = movieList,
                    onMovieClick = {
                        showModalSheet.value = !showModalSheet.value
                        viewmodel.getMovieDetails(it.imdbID)
                    },
                    onBookmarkClick = { isChecked, movie ->
                        if (!isChecked)
                            viewmodel.bookmarkMovie(movie)
                        else
                            viewmodel.removeMovie(movie)
                    }
                )

                /*LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = 135.dp)
                ) {
                    items(movieList) { movie ->
                        if (movie != null) {
                            val (isChecked, setChecked) = remember { mutableStateOf(false) }
                            MovieListItem(
                                movie = movie,
                                onMovieClick = {
                                    // call the movie detail api and trigger the bottom sheet
                                    showModalSheet.value = !showModalSheet.value
                                    viewmodel.getMovieDetails(movie.imdbID)
                                },
                                isChecked,
                                onBookmarkClick = {
                                    setChecked(!isChecked)
                                    if (!isChecked)
                                        viewmodel.bookmarkMovie(movie)
                                    else
                                        viewmodel.removeMovie(movie)
                                }
                            )
                        }
                    }
                }*/
            }

            /*if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }*/

//        Progress Bar
            /*if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }*/
        }
    }

}


@Composable
fun SearchView(state: MutableState<TextFieldValue>, viewmodel: MoviesViewmodel) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
            if (state.value.text.length > 3) {
//                viewmodel.performQuery(state.value.text, 1)
                viewmodel.setQuery(state.value.text)
            }
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search Icon",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
//                        viewmodel.performQuery("why", 1)
                        viewmodel.setQuery("why")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Clear Icon",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        maxLines = 1,
        placeholder = {
            Text(
                text = "search movie or show",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        textStyle = TextStyle(
            textAlign = TextAlign.Center,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            placeholderColor = Color.LightGray,
            cursorColor = Color.Transparent,
            leadingIconColor = Color.LightGray,
            trailingIconColor = Color.LightGray,
            backgroundColor = MaterialTheme.colors.onBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}

@Composable
fun ChipGroup(
    types: List<Type> = getAllTypes(),
    selectedType: Type? = null,
    onSelectedChanged: (String) -> Unit = {},
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(types) {
                Chip(
                    name = it.value,
                    isSelected = selectedType == it,
                    onSelectionChanged = { type ->
                        onSelectedChanged(type)
                    },
                )
            }
        }
    }
}

@Composable
fun Chip(
    name: String = "Chip",
    isSelected: Boolean = false,
    onSelectionChanged: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = if (isSelected) 24.dp else 0.dp,
        shape = RoundedCornerShape(18.dp),
        color = if (isSelected) Color.Red else MaterialTheme.colors.onBackground
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(name)
                }
            )
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.body1,
                color = if (isSelected) Color.White else Color.LightGray,
                modifier = Modifier.padding(16.dp),
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun TypeGroupPreview() {
    ChipGroup()
}