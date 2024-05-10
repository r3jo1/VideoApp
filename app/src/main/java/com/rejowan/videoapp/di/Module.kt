package com.rejowan.videoapp.di

import com.rejowan.videoapp.repository.VideoRepository
import com.rejowan.videoapp.viewmodel.VideoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val videoModule = module {
    single { VideoRepository(androidContext()) }
    viewModel { VideoViewModel(get()) }
}