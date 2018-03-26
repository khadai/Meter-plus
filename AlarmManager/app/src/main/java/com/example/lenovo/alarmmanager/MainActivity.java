package com.example.lenovo.alarmmanager;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
private TextView mTextView;
private EditText editTextMessage;
private Button buttonSend;
private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMessage =  findViewById(R.id.edittext_message);
        buttonSend =  findViewById(R.id.button_send);
        mTextView = findViewById(R.id.textView);

        mNotificationHelper = new NotificationHelper(this);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnButtonSend(editTextMessage.getText().toString());
            }
        });

        Button buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment timePicker = new android.support.v4.app.DialogFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });
        Button buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view,int hourOfDay,int minute){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);

        updateTimeText(c);
        startAlarm(c);
    }
    private void updateTimeText(Calendar c){
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        mTextView.setText(timeText);
    }

    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        if (c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
        }
    }
private void cancelAlarm(){
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(this,AlertReciver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

    alarmManager.cancel(pendingIntent);
    mTextView.setText("Alarm canceled");
}
public void sendOnButtonSend(String message){
    NotificationCompat.Builder nb = mNotificationHelper.getButtonSendNotification(message);
    mNotificationHelper.getManager().notify(1,nb.build());
}
}
