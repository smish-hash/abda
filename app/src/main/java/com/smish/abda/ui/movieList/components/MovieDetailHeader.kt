package com.smish.abda.ui.movieList.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.smish.abda.R
import com.smish.abda.data.model.moviedetail.MovieDetail
import com.smish.abda.data.model.moviedetail.Rating

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailHeader(
    movie: MovieDetail?,
    onDismissClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(colorResource(id = R.color.BackgroundYellow))
    ) {
        GlideImage(
            model = movie?.poster,
            contentDescription = "blur poster image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(225.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .blur(4.dp)
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd),
            onClick = { onDismissClick() }
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "close bottom sheet",
                modifier = Modifier
                    .size(32.dp)
                    .padding(8.dp)
                    .background(Color.DarkGray, CircleShape),
                tint = Color.LightGray,
            )
        }

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentSize()
                .padding(bottom = 8.dp),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            GlideImage(
                model = movie?.poster,
                contentDescription = "small poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(135.dp)
                    .height(150.dp),
            )
        }
    }
}

@Composable
fun MovieDetailHeaderTemp(
    movie: MovieDetail?,
    onDismissClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(colorResource(id = R.color.BackgroundYellow))
    ) {
        Image(
            painter = painterResource(id = R.drawable.smish),
            contentDescription = "blur poster image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(225.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .blur(4.dp)
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            onClick = { onDismissClick() }
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "close bottom sheet",
                modifier = Modifier
                    .size(28.dp)
                    .background(Color.DarkGray, CircleShape),
                tint = Color.LightGray,
            )
        }

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentSize()
                .padding(bottom = 8.dp),
            elevation = 6.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.smish),
                contentDescription = "small poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(140.dp)
                    .height(165.dp),
            )
        }
    }
}

/*
* MovieDetail(actors=Tajja Isen, Siera Florindo, Zachary Bloch, awards=1 win & 4 nominations, boxOffice=null, country=Canada, United States, dVD=null, director=N/A, genre=Animation, Family, imdbID=tt1105469, imdbRating=6.1, imdbVotes=928, language=English, metascore=N/A, plot=A group of characters who live at a library turn into superheroes and then go into books; using early reading skills to save the day. They later use a problem from the story one of them faced and apply it to their own lives., poster=https://m.media-amazon.com/images/M/MV5BNGU3YzVlZWMtNTZlNS00MmU4LTg2YjctN2U1N2MwZjMxN2M2XkEyXkFqcGdeQXVyODg3NDc1OTE@._V1_SX300.jpg, production=null, rated=TV-Y, ratings=[Rating(source=Internet Movie Database, value=6.1/10)], released=03 Sep 2007, response=True, error=null, runtime=30 min, title=Super Why!, type=series, website=null, writer=Angela Santomero, Samantha Freeman Alpert, Traci Paige Johnson, year=2007–2016)*/

@Preview(showBackground = false)
@Composable
fun MovieDetailHeaderPreview() {
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

    MovieDetailHeaderTemp(
        movie = movieDetail,
        onDismissClick = {}
    )
}