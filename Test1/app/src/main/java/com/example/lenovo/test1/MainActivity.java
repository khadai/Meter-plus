package com.example.lenovo.test1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mShownotificationButton;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuiler;
    PendingIntent mResultPendingIntent;
    TaskStackBuilder mTaskStackBuilder;
    Intent mResultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShownotificationButton = findViewById(R.id.btnShowNotification);

        mBuiler = new android.support.v7.app.NotificationCompat.Builder(this);
        mBuiler.setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(),R.drawable.ic_launcher_background))
                .setTicker("Показники відправлено")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Meter")
                .setContentText("Показник витрати електроенергії відправлено");

        mResultIntent = new Intent(this,MainActivity.class );
        mTaskStackBuilder = TaskStackBuilder.create(this);
        mTaskStackBuilder.addParentStack(MainActivity.this);

        mTaskStackBuilder.addNextIntent(mResultIntent);
        mResultPendingIntent = mTaskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuiler.setContentIntent(mResultPendingIntent);
        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        mShownotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotificationManager.notify(1,mBuiler.build());
            }
        });
    }
}
