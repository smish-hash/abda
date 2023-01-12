package com.smish.abda

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smish.abda.base.BaseActivity
import com.smish.abda.ui.Screen
import com.smish.abda.ui.movieBookmark.components.MovieBookmarkScreen
import com.smish.abda.ui.movieList.components.MovieListScreen
import com.smish.abda.ui.theme.AbdaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbdaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val (isBookmarkIconVisible, setIcon) = remember { mutableStateOf(true) }

                    Column {
                        TitleBar(
                            isBookmarkIconVisible,
                            onBookmarkClick = {
                                navController.navigate(Screen.MovieBookmarkScreen.route)
                            },
                            onBackClicked = {
                                navController.navigateUp()
                            }
                        )
                        NavHost(
                            navController = navController,
                            startDestination = Screen.MovieListScreen.route
                        ) {
                            composable(
                                route = Screen.MainScreen.route
                            ) {
                                setIcon(false)
                                ListRecyclerView(onMovieButtonClick = {
                                    navController.navigate(Screen.MovieListScreen.route)
                                })
                            }

                            composable(
                                route = Screen.MovieListScreen.route
                            ) {
                                setIcon(true)
                                MovieListScreen()
                            }

                            composable(
                                route = Screen.MovieBookmarkScreen.route
                            ) {
                                MovieBookmarkScreen()
                                setIcon(false)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(names: List<String>) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth(),

        ) {
        names.forEach { name ->
            Text(text = name, color = Color.LightGray)
        }
    }
}

@Composable
fun LastSeen(names: List<String>) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
    ) {
        names.forEach { name ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .background(color = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.smish),
                    contentDescription = "smish",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = name,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${name.length} minutes ago",
                        color = MaterialTheme.colors.background,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


@Composable
fun ListItem(name: String) {

    val expanded = remember {
        mutableStateOf(false)
    }

    val extraPadding by animateDpAsState(
        if (expanded.value) 24.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = Color.Gray,
        modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${name.length} minutes ago",
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )
                }

                OutlinedButton(onClick = { expanded.value = !expanded.value })
                {
                    Text(text = if (!expanded.value) "Tell me more" else "That's enough")
                }
            }

            if (expanded.value) {
                Column(
                    modifier = Modifier.padding(
                        bottom = extraPadding.coerceAtLeast(0.dp)
                    )
                ) {
                    Text(text = "You were warned before. Module was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.7.1, expected version is 1.5.1")
                }
            }
        }
    }
}

@Composable
fun ListRecyclerView(
    names: List<String> = List(25) { "$it" },
    onMovieButtonClick: () -> Unit
) {
    Column() {
        Button(
            onClick = { onMovieButtonClick() },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
        ) {
            Text(
                text = "Go to movies",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.background
            )
        }

        LazyColumn(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            items(items = names) { name ->
                ListItem(name)
            }
        }
    }
}

@Composable
fun TopBar() {

    val conte = LocalContext.current.applicationContext

    // TopAppBar Composable
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name), color = Color.White)
        },
        actions = {
            TopAppBarActionButton(
                image = R.drawable.ic_round_bookmark_border_24,
                description = "Bookmark Icon"
            ) {
                Toast.makeText(conte, "Bookmarks clicked", Toast.LENGTH_SHORT).show()
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun TopAppBarActionButton(
    image: Int,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(painter = painterResource(id = image), contentDescription = description)
    }
}

@Composable
fun TitleBar(
    isBookmarkIconVisible: Boolean,
    onBookmarkClick: () -> Unit,
    onBackClicked: () -> Unit
) {
    AbdaTheme {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
            ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentSize()
            ) {
                Row() {
                    Text(
                        text = if (isBookmarkIconVisible) stringResource(id = R.string.app_name) else "Favorites",
                        color = Color.White,
                        style = if (isBookmarkIconVisible) MaterialTheme.typography.h4 else MaterialTheme.typography.h5,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.SansSerif,
                    )
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primaryVariant)
                            .align(Alignment.Bottom)
                    )
                }
            }
            if (isBookmarkIconVisible) {
                IconButton(
                    onClick = { onBookmarkClick() },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_bookmark_border_24),
                        contentDescription = "Bookmark Icon",
                        tint = Color.White
                    )
                }
            } else {
                IconButton(
                    onClick = { onBackClicked() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Navigate up",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = { onBackClicked() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Navigate up",
                        tint = Color.White
                    )
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AbdaTheme {
        Greeting(listOf("Iron man", "Captain America", "Natasha", "Hulk", "Loki"))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLastSeen() {
    AbdaTheme {
        LastSeen(listOf("Iron man", "Captain America", "Yelena", "Hulk", "Loki"))
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PreviewListItem() {
    AbdaTheme {
        ListItem(name = "Kapoor")
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PreviewListRecyclerView() {
    AbdaTheme {
        ListRecyclerView(onMovieButtonClick = {})
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PreviewTitleBar() {
    AbdaTheme {
        TitleBar(true,{}) {}
    }
}
