package ebrahimi16153.github.com.movieapp.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ebrahimi16153.github.com.movieapp.model.ResponseMovieList
import ebrahimi16153.github.com.movieapp.server.ApiClient
import ebrahimi16153.github.com.movieapp.server.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Api service
private val api: ApiServices by lazy { ApiClient().getClient().create(ApiServices::class.java) }
private lateinit var movieList: List<ResponseMovieList.Data?>


fun getList(context:Context) {
    val callApi = api.movieList(1)
    callApi.enqueue(object : Callback<ResponseMovieList> {
        override fun onResponse(
            call: Call<ResponseMovieList>,
            response: Response<ResponseMovieList>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { itBody ->
                    itBody.data?.let { itData ->
                        if (itData.isNotEmpty()) {
                            movieList = itData
                        }
                    }
                }
            }
        }

        override fun onFailure(call: Call<ResponseMovieList>, t: Throwable) {
            Toast.makeText(context, "data can't loaded", Toast.LENGTH_SHORT).show()
        }

    })
}


@Composable
fun HomeScreen(context: Context) {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color(0xff007aff), elevation = 0.dp) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Movies", style = MaterialTheme.typography.h5.copy(color = Color.White))

        }
    }) {

        getList(context)
        MainContent(movieList = movieList)

    }
}

@Composable
fun MainContent(movieList: List<ResponseMovieList.Data?>) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(modifier = Modifier.padding(12.dp)) {
            LazyColumn {
                items(items = movieList) { movie ->


                }
            }

        }
    }
}


@Composable
fun MovieRow(movie : ResponseMovieList.Data) {

    Card(
        shape = CircleShape.copy(all = CornerSize(15.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(15.dp)
            .clickable { },
        elevation = 5.dp
    ) {

        Row() {
            AsyncImage(
                model = movie.poster,
                contentDescription = null
            )
        }
        
        
    }

}