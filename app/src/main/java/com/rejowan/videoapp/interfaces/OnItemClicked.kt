package com.rejowan.videoapp.interfaces

import com.rejowan.videoapp.model.VideoModel

interface OnItemClicked {
    fun onItemClicked(videoModel: VideoModel)
}