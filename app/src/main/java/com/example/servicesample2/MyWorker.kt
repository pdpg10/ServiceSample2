package com.example.servicesample2

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class MyWorker(context: Context, param: WorkerParameters) : Worker(context, param) {
    override fun doWork(): Result {
        val data = inputData
        repeat(3) {
            Thread.sleep(1000)
            if (isStopped)
                return Result.failure()
            Log.d("MyWorker", "doWork $it")
        }
        return Result.success()
    }


}