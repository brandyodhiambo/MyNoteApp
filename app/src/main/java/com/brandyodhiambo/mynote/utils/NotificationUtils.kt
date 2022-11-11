package com.brandyodhiambo.mynote.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.brandyodhiambo.mynote.R
import com.brandyodhiambo.mynote.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.brandyodhiambo.mynote.utils.Constants.NOTIFICATION_ID

object NotificationUtils {
    /**Send out the notification to the user
     * @param context is context
     * @param message is the actual message for the notification
     * */
    fun sendNotification(context: Context, message: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        val notificationBuilder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.teal_200))
                .setSmallIcon(R.drawable.ic_notifications)
                .setStyle(
                    NotificationCompat
                        .BigTextStyle()
                        .bigText(message)
                )

                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}