package com.fixee.vivt.application.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager
import com.fixee.vivt.R
import com.fixee.vivt.application.activity.MainActivity
import com.fixee.vivt.di.App
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class VivtFirebaseMessagingService: FirebaseMessagingService() {

    val token = App.getComponent().provideToken()

    override fun onNewToken(fcmToken: String) {
        super.onNewToken(fcmToken)
        getSharedPreferences("main", Context.MODE_PRIVATE).edit().putString("fcm_token", fcmToken).apply()
        if (token.token.isNotEmpty()) {
            // update fcm token
        }
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        sendNotification(p0.notification)
    }

    private fun sendNotification(remoteMessage: RemoteMessage.Notification?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = if (arrayOf("1", "2", "3", "4", "5").any{ remoteMessage?.channelId.toString().contains(it) }) remoteMessage?.channelId.toString() else "Default"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_notification_white)
            .setContentTitle(remoteMessage?.title)
            .setContentText(remoteMessage?.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (notificationManager.getNotificationChannel(channelId) == null) {
                notificationManager.createNotificationChannel(NotificationChannel("1", getString(R.string.push_change_schedule), NotificationManager.IMPORTANCE_DEFAULT))
                notificationManager.createNotificationChannel(NotificationChannel("2", getString(R.string.push_personal_info), NotificationManager.IMPORTANCE_DEFAULT))
                notificationManager.createNotificationChannel(NotificationChannel("3", getString(R.string.push_ads), NotificationManager.IMPORTANCE_DEFAULT))
                notificationManager.createNotificationChannel(NotificationChannel("4", getString(R.string.push_inform_mailing), NotificationManager.IMPORTANCE_DEFAULT))
                notificationManager.createNotificationChannel(NotificationChannel("5", getString(R.string.push_info_attendance), NotificationManager.IMPORTANCE_DEFAULT))
                notificationManager.createNotificationChannel(NotificationChannel("Default", getString(R.string.push_other), NotificationManager.IMPORTANCE_DEFAULT))
            }

            val current = notificationManager.getNotificationChannel(channelId)

            if (current.importance != NotificationManager.IMPORTANCE_NONE) {
                notificationManager.notify(0, notificationBuilder.build())
            }
        } else {
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)

            if (prefs.getBoolean(channelId, true)) {
                notificationManager.notify(0, notificationBuilder.build())
            }
        }
    }
}