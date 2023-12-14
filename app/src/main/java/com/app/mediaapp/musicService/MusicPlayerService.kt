package com.app.mediaapp.musicService

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.util.TimeUtils
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.app.mediaapp.R
import com.app.mediaapp.utility.Constants
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MusicPlayerService : Service() {

    private var mMediaPlayer: MediaPlayer? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> serviceStart()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun serviceStart() {
        startMusic()
        val notification = NotificationCompat.Builder(this, Constants.MUSIC_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Music is playing")
            .setContentText("Start Time : ${SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())}")
            .build()
        startForeground(1, notification)
    }

    private fun startMusic() {
        val audioUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3"
        mMediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(audioUrl)
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
            mMediaPlayer?.stop()
            mMediaPlayer?.reset()
            mMediaPlayer?.release()
            mMediaPlayer = null
        } else {
            Toast.makeText(this, "Audio not playing", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }

    enum class Actions {
        START, STOP
    }
}