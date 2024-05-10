package com.rejowan.videoapp.ui.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.rejowan.videoapp.databinding.ActivityVideoViewBinding

class VideoView : AppCompatActivity() {

    private val binding: ActivityVideoViewBinding by lazy {
        ActivityVideoViewBinding.inflate(layoutInflater)
    }

    private var title = ""
    private var videoUrl = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = intent.getStringExtra("title").toString()
        videoUrl = intent.getStringExtra("videoUrl").toString()


        binding.title.text = title
        binding.title.maxLines = 1


        val uri = Uri.parse(videoUrl)
        binding.videoView.setVideoURI(uri)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)
        mediaController.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.start()

        binding.ivBack.setOnClickListener {
            finish()
        }

    }


}