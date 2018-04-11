package com.example.lenovo.alarmmanager;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


@TargetApi(Build.VERSION_CODES.O)
public class NotificationHelper extends ContextWrapper {
public static final String buttonSendID = "buttonSendID";
public static final String buttonSendName = "Cahanal 1";
public  NotificationManager mManager;


    public NotificationHelper(Context base) {
        super(base);

            createChannels();
        }

    @TargetApi(Build.VERSION_CODES.O)
       public void createChannels() {
        NotificationChannel buttonSend = new NotificationChannel(buttonSendID, buttonSendName, NotificationManager.IMPORTANCE_DEFAULT);
        buttonSend.enableLights(true);
        buttonSend.enableVibration(true);
        buttonSend.setLightColor(R.color.colorPrimary);
        buttonSend.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(buttonSend);
    }

public NotificationManager getManager(){
        if(mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
}
public NotificationCompat.Builder getButtonSendNotification(String message){
    return new NotificationCompat.Builder(getApplicationContext(),buttonSendID)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_one);
}
}
