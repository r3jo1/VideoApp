package com.rejowan.videoapp.repository

import android.content.Context
import android.util.Log
import com.rejowan.videoapp.data.local.SQLiteDBHelper
import com.rejowan.videoapp.data.remote.RetrofitInstance
import com.rejowan.videoapp.model.VideoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepository(private val context: Context) {


    suspend fun getVideosFromAPI(): List<VideoModel> {
        return withContext(Dispatchers.IO) {
            try {
                RetrofitInstance.apiService.getVideos()
            } catch (e: Exception) {
                Log.e("VideoRepository", "getVideosFromAPI: $e")
                emptyList()
            }
        }
    }

    fun getVideosFromDB(): List<VideoModel> {
        return SQLiteDBHelper(context).getVideos()
    }

    suspend fun insertVideosToDB(videos: List<VideoModel>) {
        withContext(Dispatchers.IO) {
            SQLiteDBHelper(context).insertVideos(videos)
        }
    }

    suspend fun deleteVideosFromDB(videos: List<VideoModel>) {
        withContext(Dispatchers.IO) {
            SQLiteDBHelper(context).deleteVideos(videos)
        }
    }

    suspend fun updateVideosToDB(videos: List<VideoModel>) {
        withContext(Dispatchers.IO) {
            SQLiteDBHelper(context).updateVideos(videos)
        }


    }


}