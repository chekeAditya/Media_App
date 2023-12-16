package com.app.mediaapp.musicService

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.app.mediaapp.R
import com.app.mediaapp.utility.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MusicPlayerService : Service() {

    private var mMediaPlayer: MediaPlayer? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(Constants.TAG, "mMediaPlayer: ")
        Log.d(Constants.TAG, "onStartCommand: ")
        when (intent?.action ?: Actions.START.toString()) {
            Actions.START.toString() -> if (mMediaPlayer == null) serviceStart(intent?.getStringExtra(Constants.songUrl))
            Actions.STOP.toString() -> stopMusic()
        }
        return START_STICKY
    }

    private fun serviceStart(songUrl: String?) {
        startMusic(songUrl)
        val notification = NotificationCompat.Builder(this, Constants.MUSIC_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Music is playing")
            .setContentText("Start Time : ${SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())}")
            .build()
        startForeground(1, notification)
    }

    private fun startMusic(songUrl: String?) {
        Log.d(Constants.TAG, "startMusic: ")
        mMediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(songUrl)
            prepareAsync()
            setOnPreparedListener { start() }
            setOnErrorListener { _, what, extra ->
                Log.d("Aditya", "What: $what, Extra: $extra")
                true
            }
        }
    }

    private fun stopMusic() {
        if (mMediaPlayer?.isPlaying == true) {
            Log.d(Constants.TAG, "stopMusic: ")
            mMediaPlayer?.stop()
            mMediaPlayer?.reset()
            mMediaPlayer?.release()
            mMediaPlayer = null
        } else {
            Log.d(Constants.TAG, "stopMusic:  Not Playing")
            Toast.makeText(this, "Audio not playing", Toast.LENGTH_LONG).show()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(Constants.TAG, "onLowMemory: ")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(Constants.TAG, "onTaskRemoved: ")
    }

    override fun onTimeout(startId: Int) {
        super.onTimeout(startId)
        Log.d(Constants.TAG, "onTimeout: ")
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(Constants.TAG, "onRebind: ")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d(Constants.TAG, "onTrimMemory: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Constants.TAG, "onDestroy: ")
    }

    enum class Actions {
        START, STOP
    }
}