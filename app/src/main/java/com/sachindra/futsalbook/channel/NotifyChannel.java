package com.sachindra.futsalbook.channel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotifyChannel {
    Context context;
    public final static String login = "Channel1";

    public NotifyChannel(Context context) {
        this.context = context;
    }

    public void CreateChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            NotificationChannel channel1 = new NotificationChannel(
                    login,"Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Login Successful");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}