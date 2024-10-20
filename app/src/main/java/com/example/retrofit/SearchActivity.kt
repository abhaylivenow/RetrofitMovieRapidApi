package com.example.retrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.retrofit.data.SearchedData.MovieDetail
import com.example.retrofit.data.SearchedData.SearchedDataResponse
import com.example.retrofit.ui.theme.RetrofitTheme

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val repository by lazy {
                Repository()
            }

            val moviesViewModel: PostsViewModel by viewModels {
                PostsViewModelFactory(repository)
            }

            SearchScreenUI(viewModel = moviesViewModel)
        }
    }
}

@Composable
fun SearchScreenUI(viewModel: PostsViewModel) {
    val searchMoviesResponse = viewModel.searchedMovieList.collectAsState()

    var searchValue by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        SearchBox(searchValue, {
            searchValue = it
            // 
        })
        SearchButton({
            viewModel.getSearchedMovie(searchValue)
        })
        searchMoviesResponse.value?.let {
            SearchedMovieLC(searchMovieResponse = it)
        }
    }
}

@Composable
fun SearchBox(searchValue: String, onQueryChanged:(String) -> Unit) {
    OutlinedTextField(
        value = searchValue
        ,onValueChange = {  newTypedValue ->
            onQueryChanged(newTypedValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
        label = {
            Text(text = "Search Contact")
        },
        shape = RoundedCornerShape(28.dp),
        leadingIcon = {
            Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = null,
                modifier = Modifier.size(32.dp))
        },
        textStyle = TextStyle(color = Color.White)
    )
}

@Composable
fun SearchButton(onSearchClicked:() -> Unit) {
    Button(onClick = {
                     onSearchClicked()
    },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
        Text(text = "Search")
    }
}

@Composable
fun SearchedMovieLC(searchMovieResponse: SearchedDataResponse) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(searchMovieResponse.movieDetail) { movieDetail ->
            SearchedItemUi(movieDetail = movieDetail)
        }
    }
}

@Composable
fun SearchedItemUi(movieDetail: MovieDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoilSearchedImage(url = movieDetail.i.imageUrl)
        Text(
            text = movieDetail.movieName,
            style = TextStyle(color = Color.White, fontSize = 22.sp),
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun CoilSearchedImage(url: String) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .build(),
        contentDescription = null,
        modifier = Modifier.height(120.dp)
            .width(80.dp)
            .clip(RoundedCornerShape(6.dp)),
        contentScale = ContentScale.Crop
    )
}








