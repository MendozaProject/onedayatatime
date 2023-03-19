package com.example.onedayatatime

import android.app.Application
import androidx.room.Room
import com.example.onedayatatime.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}