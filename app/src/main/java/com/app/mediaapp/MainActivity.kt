package com.app.mediaapp

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.app.mediaapp.databinding.ActivityMainBinding
import com.app.mediaapp.musicService.DozeModeReceiver
import com.app.mediaapp.musicService.MusicPlayerService
import com.app.mediaapp.utility.Constants


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dozeModeReceiver: DozeModeReceiver = DozeModeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK, Manifest.permission.WAKE_LOCK), 0)
        }

        val filter = IntentFilter(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED)
        registerReceiver(dozeModeReceiver, filter)

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

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dozeModeReceiver)
        Log.d(Constants.TAG, "onDestroy")
    }

    override fun onResume() {
        super.onResume()
        Log.d(Constants.TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(Constants.TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(Constants.TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(Constants.TAG, "onRestart")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(Constants.TAG, "onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(Constants.TAG, "onDetachedFromWindow")
    }
}