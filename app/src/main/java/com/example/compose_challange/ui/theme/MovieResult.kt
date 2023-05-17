package com.example.compose_challange.ui.theme

import com.google.gson.annotations.SerializedName

class MovieResult {

    @SerializedName("title")
    var title: String = ""

    @SerializedName("video_playback_url")
    var video_playback_url: String = ""

    @SerializedName("watched_duration")
    var watched_duration: Int = 0


}