package ebrahimi16153.github.com.movieapp.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import ebrahimi16153.github.com.movieapp.model.ResponseMovieList
import ebrahimi16153.github.com.movieapp.server.ApiClient
import ebrahimi16153.github.com.movieapp.server.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Api service
private val api: ApiServices by lazy { ApiClient().getClient().create(ApiServices::class.java) }


@Composable
fun HomeScreen( navController: NavHostController) {
    val movieList =  mutableListOf<ResponseMovieList.Data?>()
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color(0xff007aff), elevation = 0.dp) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Movies", style = MaterialTheme.typography.h5.copy(color = Color.White))

        }
    }) {

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
                                movieList.addAll(itData)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMovieList>, t: Throwable) {
               throw java.lang.NullPointerException("Null")
            }

        })
        
         MainContent(navController= navController,movieList = movieList)
    }
}

@Composable
fun MainContent(navController: NavController, movieList: MutableList<ResponseMovieList.Data?>) {
    Surface(
        color = MaterialTheme.colors.background,
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            LazyColumn {

                items(items = movieList) { movie ->
                    MovieRow( movie) {
//                        navController.navigate(route = MoviesScreen.DetailsScreen.name+"/$movie")
//                        Log.e("TAG", "itemClick")
                    }
                }

            }
        }

    }
}


@Composable
fun MovieRow(movie : ResponseMovieList.Data?, onClick: () -> Unit) {

    Card(
        shape = CircleShape.copy(all = CornerSize(15.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(15.dp)
//                .clip(shape = CircleShape.copy(all = CornerSize(16.dp)))
            .clickable { onClick() },
        elevation = 5.dp,
    ) {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .background(MaterialTheme.colors.background)
                    .size(100.dp),
                shape = RectangleShape,
//                    elevation = 4.dp
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "image icon",
                    tint = Color(0xff007aff)
                )
            }
            Text(text = movie!!.title!!)
        }


    }

}