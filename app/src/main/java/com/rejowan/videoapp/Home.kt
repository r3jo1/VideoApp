package com.rejowan.videoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rejowan.videoapp.databinding.ActivityHomeBinding
import com.rejowan.videoapp.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val videoViewModel: VideoViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


      //  videoViewModel.getVideos()

        videoViewModel.videos.observe(this) {
            if (it.isNotEmpty()) it.forEach { video ->
                Log.e("Video", video.title)
            }
        }
    }


}



