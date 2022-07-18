package com.example.workoutprogramapp

import android.app.Notification
import android.app.NotificationChannel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast

class AlertReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        /*val requestCode = intent!!.getIntExtra("REQUEST_CODE", -1)
        Toast.makeText(context, "Alarm fired with request code"+requestCode, Toast.LENGTH_LONG).show()
        */
        val mp = MediaPlayer.create(context, R.raw.slow_morning)
        mp.start()
    }

}