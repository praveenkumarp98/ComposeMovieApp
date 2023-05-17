package com.example.compose_challange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.compose_challange.MainActivity.Companion.IMAGE_BASE_URL
import com.example.compose_challange.ui.theme.Api.Results
import com.example.compose_challange.ui.theme.Compose_challangeTheme
import com.google.gson.Gson

class MovieDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Compose_challangeTheme {
                // A surface container using the 'background' color from the theme
               detailScreenUI()
            }
        }


    }

    @Composable
    private fun detailScreenUI() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            val movie = Gson().fromJson(intent.getStringExtra("movie"), Results::class.java)

            Box {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {

                    AsyncImage(
                        model = IMAGE_BASE_URL + movie.posterPath,
                        contentDescription = movie.title
                    )

                    Text(
                        text = movie.originalTitle ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )

                    Text(
                        text = "Overview : " + movie.overview,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )

                    Text(
                        text = "Release Date : " + movie.releaseDate,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )

                    Text(
                        text = "Rating : " + movie.voteAverage.toString() + "/10",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )

                    Text(
                        text = "Number of votes : " + movie.voteCount,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }

        }
    }
}