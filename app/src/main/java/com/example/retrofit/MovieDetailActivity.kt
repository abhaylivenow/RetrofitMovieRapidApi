package com.example.retrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.retrofit.data.MovieDetail
import com.example.retrofit.data.genre.Genre
import com.example.retrofit.ui.theme.RetrofitTheme

class MovieDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val id = intent.getStringExtra("id") ?: ""
            val url = intent.getStringExtra("url") ?: ""
            val name = intent.getStringExtra("name") ?: ""

            val repository by lazy {
                Repository()
            }

            val moviesViewModel: PostsViewModel by viewModels {
                PostsViewModelFactory(repository)
            }

            if(!id.isNullOrBlank()) {
                moviesViewModel.getMovieGenre(id)
            }
            DetailScreen(url = url, name = name, moviesViewModel)
        }
    }
}

@Composable
fun DetailScreen(url: String, name: String, viewModel: PostsViewModel) {

    val movieGenreResponse = viewModel.movieGenreFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MovieDetailCard(url)
        MovieNameText(name)

        movieGenreResponse.value?.data?.title?.titleGenres?.genres?.let {
            MovieGenreText(it)
        }
    }
}

@Composable
fun MovieDetailCard(url: String) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .height(400.dp)
            .width(267.dp)
    ) {
        CoilDetailImage(url = url)
    }
}

@Composable
fun CoilDetailImage(url: String) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun MovieNameText(name: String) {
    Text(
        text = name,
        style = TextStyle(color = Color.White, fontSize = 24.sp),
        modifier = Modifier.padding(top = 12.dp)
    )
}

@Composable
fun MovieGenreText(genreList: List<Genre>) {

    var genreText = ""

    genreList.forEach {
        genreText += it.genre.text + ", "
    }

    Text(
        text = "( $genreText )",
        style = TextStyle(color = Color.LightGray, fontSize = 20.sp),
        modifier = Modifier.padding(top = 6.dp)
    )
}








