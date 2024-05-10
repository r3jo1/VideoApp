package com.rejowan.videoapp.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.rejowan.videoapp.databinding.ActivityHomeBinding
import com.rejowan.videoapp.interfaces.OnItemClicked
import com.rejowan.videoapp.model.VideoModel
import com.rejowan.videoapp.ui.adapter.VideoAdapter
import com.rejowan.videoapp.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val videoViewModel: VideoViewModel by viewModel()

    private lateinit var adapter: VideoAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { _: Boolean ->
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)


        adapter = VideoAdapter(mutableListOf(), object : OnItemClicked {
            override fun onItemClicked(videoModel: VideoModel) {
                startActivity(
                    Intent(this@Home, VideoView::class.java).putExtra("title", videoModel.title)
                        .putExtra("videoUrl", videoModel.videoUrl)
                )

            }
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        videoViewModel.videos.observe(this) {
            if (it.isNotEmpty()) {
                adapter.updateList(it)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            videoViewModel.getVideos()
            Toast.makeText(this, "Updating Videos...", Toast.LENGTH_SHORT).show()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        askNotificationPermission()


    }


    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


}