package com.smish.abda.ui.movieList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.smish.abda.data.model.movie.Search
import com.smish.abda.ui.theme.AbdaTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieListItem(
    movie: Search,
    onMovieClick: (Search) -> Unit,
    isChecked: Boolean,
    onBookmarkClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .clickable { onMovieClick(movie) }
                .padding(12.dp)
                .wrapContentSize()
        ) {
            Box() {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = MaterialTheme.colors.background,
                ) {
                    GlideImage(
                        model = movie.poster,
                        contentDescription = "movie poster",
                        modifier = Modifier
                            .height(150.dp)
                            .width(135.dp),
                        // Crop, Fit, Inside, FillHeight, FillWidth, None
                        contentScale = ContentScale.Crop
                    )
                }

                IconToggleButton(
                    checked = isChecked, onCheckedChange = { onBookmarkClick() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(2.dp)
                ) {
                    Icon(
                        imageVector = if (isChecked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "bookmark",
                        tint = if (isChecked) Color.Red else Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Text(
                modifier = Modifier
                    .width(135.dp)
                    .padding(vertical = 3.dp),
                text = movie.title,
                style = MaterialTheme.typography.h6,
                color = Color.White
            )

            Text(
                text = movie.type,
                style = MaterialTheme.typography.body2,
                color = Color.LightGray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMovieListItem() {
    val (isChecked, setChecked) = remember { mutableStateOf(false) }
    AbdaTheme {
        MovieListItem(
            Search("123", "fdowbi", "Smishra", "movie", "2021"),
            onMovieClick = {},
            isChecked,
            onBookmarkClick = { setChecked(!isChecked) }
        )
    }
}