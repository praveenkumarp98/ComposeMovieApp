package com.example.compose_challange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.compose_challange.ui.theme.Api.ApiInterface
import com.example.compose_challange.ui.theme.Api.MovieResult
import com.example.compose_challange.ui.theme.Api.Results
import com.example.compose_challange.ui.theme.Compose_challangeTheme
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    companion object {
        val BASE_URL = "https://api.themoviedb.org/"
        val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
    }
    val API_KEY = "70af851c6b659458ba8a2b5ce360424d"
    val CATEGORY = "popular"
    val LANGUAGE = "en-US"
    var PAGE = 1
    var result : MovieResult? = null

    val _movieList = MutableStateFlow<MovieResult?>(null)
    val movieList = _movieList.asStateFlow()
    var context : Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this@MainActivity
        setContent {
            Compose_challangeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    LaunchedEffect(Unit ){
                        createRetrofit()
                    }

                    val list = movieList.collectAsState()
                    Box {
                        if (list.value == null) {
                            Box (
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {
                            MovieList(movieResults = list.value)
                        }
                    }

                }
            }
        }



    }

    private fun createRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()


        val restAdapter = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        val apiInterface : ApiInterface = restAdapter.create(ApiInterface::class.java)

        lifecycleScope.launch(Dispatchers.IO){
            try {
                result = apiInterface.getMoviesBasedOnCategories(
                    CATEGORY,
                    API_KEY,
                    LANGUAGE,
                    PAGE
                )

                _movieList.value = result
                Log.i("rexx","result : "+Gson().toJson(result))
                Log.i("rexx","result : "+ result!!.results.size)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }



    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Compose_challangeTheme {
            Greeting("Android")
        }
    }

    @Composable
    fun MovieList(movieResults: MovieResult?) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            movieResults?.results?.let {
                items(
                    count = it?.size!!,
                    itemContent = { index ->
                        ItemView(index = index, result = it?.get(index))
                        //Text(text = "Item ${it?.get(index)?.title?:"hhh"}")
                    }
                )
            }
        }
    }

    @Composable
    fun ItemView(index: Int, result: Results){

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().clickable {
                onClickItem(result)
            }
        ) {

            AsyncImage(
                model = IMAGE_BASE_URL+result.backdropPath,
                contentDescription = result.title
            )

            Text(
                text = result.originalTitle?:"",
                modifier = Modifier
                    //.background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }


    }

    fun onClickItem(result: Results) {
        Log.i("rexx","click : "+Gson().toJson(result))

        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("movie", Gson().toJson(result))
        startActivity(intent)
    }


}


