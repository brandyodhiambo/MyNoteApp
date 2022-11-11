package com.brandyodhiambo.mynote

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.brandyodhiambo.mynote.workmanager.startOnetimeWorkRequest
import com.brandyodhiambo.mynote.workmanager.startPeriodicWorkRequest
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class NoteApp :Application(), Configuration.Provider{
    override fun onCreate() {
        super.onCreate()
        startPeriodicWorkRequest(this)
        Timber.plant(Timber.DebugTree())
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }


}