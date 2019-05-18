package id.pamoyanan_dev.gitsalarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.support.v4.app.NotificationCompat

object NotificationUtil {

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    fun createNotificationChannel(context: Context) {
        // Create a notification manager object.
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, "ChannelId")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Ane muncul setelah alarm diset 5 detik dari waktu sekarang")
            .setContentText("Yoyoy")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(
                "ChannelId",
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notifies every 15 minutes to stand up and walk"
            mNotificationManager.createNotificationChannel(notificationChannel)
        }

        mNotificationManager.notify(0, builder.build())
    }

}