package com.brandyodhiambo.mynote.workmanager

import android.content.Context
import androidx.work.*
import com.brandyodhiambo.mynote.workmanager.NoteWorker.Companion.WORK_NAME
import java.util.concurrent.TimeUnit

fun startOnetimeWorkRequest(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val workRequest = OneTimeWorkRequestBuilder<NoteWorker>()
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}

fun startPeriodicWorkRequest(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<NoteWorker>(
        15,
        TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        WORK_NAME,
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )
}