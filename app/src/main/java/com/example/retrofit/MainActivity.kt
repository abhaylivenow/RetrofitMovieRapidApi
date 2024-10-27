package com.example.retrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
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
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val repository by lazy {
                Repository()
            }

            val moviesViewModel: PostsViewModel by viewModels {
                PostsViewModelFactory(repository)
            }

            MainScreenUi(viewModel = moviesViewModel)

            val isLoading = moviesViewModel.homeLoadingLD.collectAsState().value

            if(isLoading) {
                ShowLoader()
            } else {
                // hideLoader
            }
        }
    }
}

@Composable
@Preview
fun ShowLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MainScreenUi(viewModel: PostsViewModel) {

    val context = LocalContext.current

    val listOfMovieDetailLD = viewModel.movieFromIdFlow.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        listOfMovieDetailLD?.let {
            GridMovieLayout(movies = listOfMovieDetailLD.value)
        }
    }
    FabSearch({
        context.startActivity(
            Intent(context, SearchActivity::class.java)
        )
    })
}
// game of thrones

@Composable
fun GridMovieLayout(movies: List<MovieDetail>) {

    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movieDetail ->
            MovieCard(movieDetail) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("name", movieDetail.data.title.titleText.text)
                intent.putExtra("url", movieDetail.data.title.primaryImage.url)
                intent.putExtra("id", movieDetail.data.title.id)
                context.startActivity(intent)
            }
        }
    }

}

@Composable
fun MovieCard(movieDetail: MovieDetail, onMovieClicked:() -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.clickable {
            onMovieClicked()
        }
    ) {
        CoilImage(url = movieDetail.data.title.primaryImage.url)
    }
}

@Composable
fun CoilImage(url: String) {
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
fun FabSearch(onButtonClicked:() -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(onClick = {
                   onButtonClicked()
        }, modifier = Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = null)
        }
    }
}

//a32882bd46msh090023205a198bap19e7e6jsn89926a0de99b
//tt11315808
// title/v2/get-details
// title/get-most-popular-movies









