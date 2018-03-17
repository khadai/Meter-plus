package com.example.application.noticationexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mShowNotificationButton;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    PendingIntent mResultPendingIntent;
    TaskStackBuilder mTaskStackBuilder;
    Intent mResultIntent;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mShowNotificationButton = findViewById(R.id.btnShowNotification);

        mBuilder = new android.support.v7.app.NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher); //set notification icon
        mBuilder.setContentTitle("Notification Title"); //set notification title
        mBuilder.setContentText("Notification Detail..."); //set notification content/detail


        mResultIntent = new Intent (this, MainActivity.class);
        mTaskStackBuilder = TaskStackBuilder.create(this);
        mTaskStackBuilder.addParentStack(MainActivity.this);


        //Add the intent that will start the activity to the top of stack
        mTaskStackBuilder.addNextIntent(mResultIntent);
        mResultPendingIntent = mTaskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(mResultPendingIntent);


        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        //show notification on button click
        mShowNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotificationManager.notify(1,mBuilder.build());
            }
        });
    }
}
