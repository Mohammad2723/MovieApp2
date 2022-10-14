package ebrahimi16153.github.com.movieapp.server

import ebrahimi16153.github.com.movieapp.model.ResponseMovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("movies")
    fun movieList(@Query("page") page:Int):Call<ResponseMovieList>

}