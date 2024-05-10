package com.rejowan.videoapp.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rejowan.videoapp.model.VideoModel
import com.rejowan.videoapp.repository.VideoRepository
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

class VideoViewModel(private val videoRepository: VideoRepository) : ViewModel() {

    private val _videos = MutableLiveData<List<VideoModel>>()
    val videos: MutableLiveData<List<VideoModel>> get() = _videos

    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval: Long = 5 * 60 * 1000

    init {

        getAndUpdateVideos()

        handler.postDelayed(object : Runnable {
            override fun run() {
                Log.e("VideoViewModel", "Updating videos runnable")
                getAndUpdateVideos()
                handler.postDelayed(this, updateInterval)
            }
        }, updateInterval)

    }

    fun getAndUpdateVideos() {

        viewModelScope.launch {
            try {
                val videosFromAPI = videoRepository.getVideosFromAPI()
                val videosFromDB = videoRepository.getVideosFromDB()

                if (videosFromDB.isEmpty()) {
                    videoRepository.insertVideosToDB(videosFromAPI)
                    _videos.value = videosFromAPI
                    Log.e("VideoViewModel", "DB empty, Inserted and returned videos from API")
                    return@launch
                }

                val newVideos = mutableListOf<VideoModel>()
                val updatedVideos = mutableListOf<VideoModel>()

                for (apiVideo in videosFromAPI) {
                    val existingVideo = videosFromDB.find { it.id == apiVideo.id }

                    if (existingVideo == null) {
                        newVideos.add(apiVideo)
                    } else if (existingVideo != apiVideo) {
                        updatedVideos.add(apiVideo)
                    }
                }

                if (newVideos.isNotEmpty()) {
                    videoRepository.insertVideosToDB(newVideos)
                    Log.e("VideoViewModel", "" + newVideos.size + " New videos added")
                } else {
                    Log.e("VideoViewModel", "No new videos")
                }

                if (updatedVideos.isNotEmpty()) {
                    videoRepository.updateVideosToDB(updatedVideos)
                    Log.e("VideoViewModel", "" + updatedVideos.size + " Updated videos")
                } else {
                    Log.e("VideoViewModel", "No updated videos")
                }

                _videos.value = videosFromAPI

            } catch (e: Exception) {
                Log.e("VideoViewModel", e.toString())
            }
        }

    }

    fun getVideos() {
        viewModelScope.launch {

            val videosFromDB = videoRepository.getVideosFromDB()
            if (videosFromDB.isEmpty()) {
                try {
                    val videosFromAPI = videoRepository.getVideosFromAPI()
                    videoRepository.insertVideosToDB(videosFromAPI)
                    _videos.value = videosFromAPI

                    Log.e("VideoViewModel", "Video Source API")
                } catch (e: Exception) {
                    Log.e("VideoViewModel", e.toString())
                }
            } else {
                _videos.value = videosFromDB

                Log.e("VideoViewModel", "Video Source DB")
            }

        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.e("VideoViewModel", "onCleared")
        handler.removeCallbacksAndMessages(null)
    }

}