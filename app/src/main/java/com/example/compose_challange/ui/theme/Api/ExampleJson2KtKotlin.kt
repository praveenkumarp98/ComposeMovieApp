package com.example.compose_challange.ui.theme.Api

import com.google.gson.annotations.SerializedName


data class MovieResult (

  @SerializedName("page"          ) var page         : Int?               = null,
  @SerializedName("results"       ) var results      : ArrayList<Results> = arrayListOf(),
  @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
  @SerializedName("total_results" ) var totalResults : Int?               = null

)