package com.app.mediaapp.musicService

import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.app.mediaapp.utility.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(Constants.TAG, "onNewToken: ")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Log the message for debugging purposes
        Log.d(Constants.TAG, "From: ")
        serviceStart()
    }

    private fun serviceStart() {
        Intent(applicationContext, MusicPlayerService::class.java).also {
            it.action = MusicPlayerService.Actions.START.toString()
            startService(it)
        }
    }

    override fun handleIntent(intent: Intent) {
        try {
            if (intent.extras != null) {
                Log.d(Constants.TAG, "handleIntent: 1")
                val builder = RemoteMessage.Builder("MessagingService")
                for (key in intent.extras!!.keySet()) {
                    Log.d(Constants.TAG, "handleIntent: 4")
                    builder.addData(key, intent.extras!!.getString(key).toString())
                }
                onMessageReceived(builder.build())
            } else {
                Log.d(Constants.TAG, "handleIntent: 2")
                super.handleIntent(intent)
            }
        } catch (e: Exception) {
            Log.d(Constants.TAG, "handleIntent: 3 ${e.message}")
            super.handleIntent(intent)
        }
    }

}
