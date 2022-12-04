package com.smish.abda.ui.movieList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smish.abda.R
import com.smish.abda.data.model.moviedetail.MovieDetail
import com.smish.abda.data.model.moviedetail.Rating
import com.smish.abda.data.model.moviedetail.toArray

@Composable
fun MovieDetailBody(
    movie: MovieDetail
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 24.dp)
            .background(colorResource(id = R.color.BackgroundYellow))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = movie?.title ?:"",
                style = MaterialTheme.typography.h6,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = movie?.year ?: "",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.SansSerif,
                color = Color.Gray
            )
            Row() {
                Text(
                    text = movie?.runtime ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if(movie?.runtime == null) (movie?.rated ?: "") else " | ${movie.rated}",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Gray
                )
            }

            Text(
                text = movie?.genre?:"",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.SansSerif,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Imdb: ${movie?.imdbRating}",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Synopsis",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.body1,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie?.plot ?: "Unavailable",
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.body2,
                fontFamily = FontFamily.SansSerif,
                color = Color.Gray,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Cast & Crew",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.body1,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
            ) {
                items(items = movie.toArray(movie.actors ?: ""), itemContent =  { actor ->
                    if (actor.isNotBlank()) {
                        Text(
                            text = actor,
                            style = MaterialTheme.typography.body2,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                })
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun MovieDetailBodyPreview() {
    val movieDetail = MovieDetail(
        actors = "Tajja Isen, Siera Florindo, Zachary Bloch",
        awards = "1 win & 4 nominations",
        boxOffice = "null",
        country = "Canada, United States",
        dVD = "null",
        director = "N/A",
        genre = "Animation, Family",
        imdbID = "tt1105469",
        imdbRating = "6.1",
        imdbVotes = "928",
        language = "English",
        metascore = "N/A",
        plot = "A group of characters who live at a library turn into superheroes and then go into books; using early reading skills to save the day. They later use a problem from the story one of them faced and apply it to their own lives.",
        poster = "https://m.media-amazon.com/images/M/MV5BNGU3YzVlZWMtNTZlNS00MmU4LTg2YjctN2U1N2MwZjMxN2M2XkEyXkFqcGdeQXVyODg3NDc1OTE@._V1_SX300.jpg",
        production = "null",
        rated = "TV-Y",
        ratings = listOf(Rating(source = "Internet Movie Database", value = "6.1/10")),
        released = "03 Sep 2007",
        response = "True",
        error = "null",
        runtime = "30 min",
        title = "Super Why!",
        type = "series",
        website = "null",
        writer = "Angela Santomero, Samantha Freeman Alpert, Traci Paige Johnson",
        year = "2007–2016"
    )

    MovieDetailBody(
        movie = movieDetail
    )
}