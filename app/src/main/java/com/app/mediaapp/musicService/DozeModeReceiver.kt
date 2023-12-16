package com.app.mediaapp.musicService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log


class DozeModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: BroadcastReceiver")
        if (intent.action == PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED) {
            val manager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            val isDozeMode = manager.isDeviceIdleMode
            Log.d(TAG, "Doze Mode: " + if (isDozeMode) "ON" else "OFF")
        }
    }

    companion object {
        const val TAG = "Aditya"
    }
}
