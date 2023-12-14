package com.app.mediaapp

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.app.mediaapp.databinding.ActivityMainBinding
import com.app.mediaapp.musicService.MusicPlayerService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK), 0)
        }

        binding.startMusicButton.setOnClickListener { serviceStart() }
        binding.stopMusicButton.setOnClickListener { serviceStop() }
    }

    private fun serviceStart() {
        Intent(applicationContext, MusicPlayerService::class.java).also {
            it.action = MusicPlayerService.Actions.START.toString()
            startService(it)
        }
    }

    private fun serviceStop() {
        Intent(applicationContext, MusicPlayerService::class.java).also {
            it.action = MusicPlayerService.Actions.STOP.toString()
            startService(it)
        }
    }
}