package com.example.compose_challange.ui.theme.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("/3/movie/{category}")
    suspend fun getMoviesBasedOnCategories(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieResult

}