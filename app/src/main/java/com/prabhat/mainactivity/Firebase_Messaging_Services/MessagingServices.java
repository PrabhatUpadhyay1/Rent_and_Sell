package com.prabhat.mainactivity.Firebase_Messaging_Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.prabhat.mainactivity.Activities.Home_Page;
import com.prabhat.mainactivity.R;

public class MessagingServices extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showMessage(remoteMessage.getNotification().getBody());
    }

    public void showMessage(String message) {

        createChannel();

        Intent intent = new Intent(this, Home_Page.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, "prabhat.mainactivity.Firebase_Messaging_Services.test")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Rent and Sell")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }

    public void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("prabhat.mainactivity.Firebase_Messaging_Services.test", "chanel 1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("");
            channel.enableLights(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}

