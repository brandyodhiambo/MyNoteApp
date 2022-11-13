package com.brandyodhiambo.mynote

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.brandyodhiambo.mynote.workmanager.startPeriodicWorkRequest
import com.brandyodhiambo.mynote.workmanager.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.brandyodhiambo.mynote.workmanager.utils.Constants.NOTIFICATION_NAME
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class NoteApp :Application(), Configuration.Provider{
    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)
        }
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