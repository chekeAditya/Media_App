package com.app.mediaapp.musicService

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import com.app.mediaapp.utility.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var mMediaPlayer: MediaPlayer? = null

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.priority == RemoteMessage.PRIORITY_HIGH) {
            // Handle high-priority message
            // For example, check if the music service needs to be restarted
            startMusicServiceIfNeeded()
        }
    }

    private fun startMusicServiceIfNeeded() {
        // Implement logic to check if the MusicPlayerService needs to be started
        // and start it if necessary
        startMusic()
    }

    private fun startMusic() {
        Log.d(Constants.TAG, "startMusic: ")
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
}
