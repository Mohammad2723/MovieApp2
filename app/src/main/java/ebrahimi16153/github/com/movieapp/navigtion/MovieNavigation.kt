package ebrahimi16153.github.com.movieapp.navigtion

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ebrahimi16153.github.com.movieapp.screens.HomeScreen

@Composable
fun MovieNavigation(){
    // Nav controller
    val navController = rememberNavController()

    //navHost & NavaGraph
    NavHost(navController = navController, startDestination = MovieScreen.HomeScreen.name ){

        //home
        composable(MovieScreen.HomeScreen.name){
            HomeScreen(navController = navController )
        }
    }
}