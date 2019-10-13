package com.example.servicesample2

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyService : IntentService("MyService") {

    override fun onHandleIntent(p0: Intent?) {
        repeat(10) {
            Log.d("MyService", "sleep $it")
            Thread.sleep(1000)
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
