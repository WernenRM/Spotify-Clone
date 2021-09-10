package com.wernen.spotifyclone.exoplayer

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Looper
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.*
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil
import com.google.android.exoplayer2.util.NotificationUtil.createNotificationChannel
import com.wernen.spotifyclone.R
import com.wernen.spotifyclone.others.Constants.CHANEL_ID
import com.wernen.spotifyclone.others.Constants.NOTIFICATION_CHANNEL_ID
import com.wernen.spotifyclone.others.Constants.NOTIFICATION_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Handler

class MusicNotificationManager(
    private val context: Context,
    sessionToken: MediaSessionCompat.Token,
    notificationListener: PlayerNotificationManager.NotificationListener,
    private val newSongCallback: () -> Unit
) {

    private val notificationManager: PlayerNotificationManager


    init {
        val mediaController = MediaControllerCompat(context, sessionToken)

//        val notificationBuilder = NotificationCompat.Builder(context)
//            .setContentTitle(context.resources.getString(R.string.notification_channel_name)).
//            setContentText(context.resources.getString(R.string.notification_channel_description))
//            .setAutoCancel(true)


//         notificationManager = PlayerNotificationManager.Builder(
//            context,
//            NOTIFICATION_CHANNEL_ID,
//            R.string.notification_channel_name,
//            R.string.notification_channel_description,
//            NOTIFICATION_ID,
//            DescriptionAdapter(mediaController),
//            notificationListener
//        ).apply {
//            setSmallIcon(R.drawable.ic_music)
//        setMediaSessionToken(sessionToken)
//        }
//
//        notificationManager = PlayerNotificationManager.Builder(context, NOTIFICATION_ID, CHANEL_ID )

         notificationManager =
            PlayerNotificationManager.Builder(context, NOTIFICATION_ID, CHANEL_ID)
                .setChannelNameResourceId(R.string.notification_channel_name)
                .setChannelDescriptionResourceId(R.string.notification_channel_description)
                .setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
                .setNotificationListener(notificationListener)
                .setSmallIconResourceId(R.drawable.ic_music)
                .build()

    }

    fun showNotification(player: Player) {
        CoroutineScope(Dispatchers.Main)
            .launch {
            notificationManager.setPlayer(player)
        }

    }

    private inner class DescriptionAdapter(
        private val mediaController: MediaControllerCompat
    ) : PlayerNotificationManager.MediaDescriptionAdapter {

        override fun getCurrentContentTitle(player: Player): CharSequence {
            newSongCallback()
            return mediaController.metadata.description.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return mediaController.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return mediaController.metadata.description.subtitle.toString()
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            Glide.with(context).asBitmap()
                .load(mediaController.metadata.description.iconUri)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        callback.onBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) = Unit
                })
            return null
        }
    }

}
