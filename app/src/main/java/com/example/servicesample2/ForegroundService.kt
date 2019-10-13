package com.example.servicesample2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.concurrent.Executors


const val CHANNEL_ID = "ForegroundService Channel"
const val NOTIFICATION_ID = 1003

class ForegroundService : Service() {
    private val handler = Handler()
    private val executor = Executors.newFixedThreadPool(1)
    override fun onBind(p0: Intent?) = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand")
        showNotification()
//        handler.post(Task())
        executor.execute(Task())
        return START_STICKY
    }

    private fun showNotification() {
        val notifyService = NotificationManagerCompat.from(this)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Runing service")
            .setContentText("Some desc")
            .setSmallIcon(R.drawable.ic_android)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_DEFAULT)
            notifyService.createNotificationChannel(channel)
        }
        startForeground(NOTIFICATION_ID, builder.build())
    }

    inner class Task : Runnable {
        var counter = 0
        override fun run() {
            counter++
            repeat(10) {
                Thread.sleep(1000)
                Log.d("ForegroundService", "run $it")
            }
            stopForeground(true)

//            handler.postDelayed(this, 1000)
        }

    }
}