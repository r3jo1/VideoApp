package com.rejowan.videoapp.data.remote

import com.rejowan.videoapp.model.VideoModel
import retrofit2.http.GET

interface RetrofitApiService {
    @GET("poudyalanil/ca84582cbeb4fc123a13290a586da925/raw/videos.json")
    suspend fun getVideos(): List<VideoModel>
}