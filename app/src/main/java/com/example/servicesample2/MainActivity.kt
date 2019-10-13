package com.example.servicesample2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startWorker()
        }
        fab.setOnLongClickListener {
            WorkManager.getInstance(this)
                .cancelAllWork()
            true
        }
    }

    private fun startWorker() {
        val inputData = Data.Builder()
            .putInt("a", 1)
            .putBoolean("b", false)
            .build()
        val request = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInitialDelay(3L, TimeUnit.SECONDS)
            .setInputData(inputData)
            .build()

        val request2 = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInitialDelay(3L, TimeUnit.SECONDS)
            .build()

        val request3 = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInitialDelay(3L, TimeUnit.SECONDS)
            .build()

        val periodReq = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            15 * 60,
            TimeUnit.SECONDS
        )
            .addTag("mytag")
            .build()
        WorkManager
            .getInstance(this)
            .beginWith(request)
            .then(listOf(request2, request3))
            .enqueue()
            .state
            .observe(this, Observer {
                Log.d("MainActivity", "startWorker $it")
            })


    }

    private fun startService1() {
        Intent(this, ForegroundService::class.java)
            .apply {
                ContextCompat.startForegroundService(this@MainActivity, this)
            }
    }
}
