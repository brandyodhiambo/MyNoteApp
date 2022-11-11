package com.brandyodhiambo.mynote.workmanager

import android.content.Context
import androidx.work.*
import com.brandyodhiambo.mynote.utils.Constants.WORK_NAME
import java.util.concurrent.TimeUnit

fun startOnetimeWorkRequest() {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val workRequest = OneTimeWorkRequestBuilder<NoteWorker>()
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance().enqueue(workRequest)
}

fun startPeriodicWorkRequest(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<NoteWorker>(
        1,
        TimeUnit.DAYS
    )
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        WORK_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}